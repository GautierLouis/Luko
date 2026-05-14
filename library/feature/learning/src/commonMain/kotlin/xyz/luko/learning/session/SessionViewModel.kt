package xyz.luko.learning.session

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.luko.designsystem.components.attrs.FrequencyLevel
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.DictionaryWithGraphic
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.Graphic
import xyz.luko.domain.model.Point
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.SessionResponse
import xyz.luko.domain.model.Stroke
import xyz.luko.domain.repository.CharacterRepository
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.learning.builder.QuestionCount
import xyz.luko.learning.routing.LearningInternalRoute
import xyz.luko.learning.session.SessionScreenEvent.Finish
import xyz.luko.learning.session.SessionScreenEvent.Next
import xyz.luko.learning.session.SessionScreenEvent.Reload
import xyz.luko.learning.session.SessionScreenEvent.Reset
import xyz.luko.learning.session.SessionScreenEvent.StrokeCompleted
import xyz.luko.learning.session.SessionScreenEvent.ToggleLeaveDialog
import xyz.luko.learning.session.usecase.AccuracyCalculatorUseCase
import xyz.luko.learning.session.usecase.CalculateScoreUseCase
import xyz.luko.navigation.AppNavigation
import kotlin.time.Clock
import kotlin.time.Instant

internal class SessionViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: CharacterRepository,
    private val sessionRepository: SessionRepository,
    private val analyzeUserDrawing: AccuracyCalculatorUseCase,
    private val scoreCalculator: CalculateScoreUseCase,
) : ViewModel() {

    private val descriptor = LearningInternalRoute.SessionRoute.serializer().descriptor

    private val levels: List<FrequencyLevel> =
        (savedStateHandle[descriptor.getElementName(0)] as? String)
            ?.split(",")
            ?.map { FrequencyLevel.valueOf(it.trim()) }
            ?: FrequencyLevel.entries

    private val difficulty: DifficultyLevel =
        (savedStateHandle[descriptor.getElementName(1)] as? String)
            ?.let { DifficultyLevel.valueOf(it) }
            ?: DifficultyLevel.EASY

    private val limit: QuestionCount = (savedStateHandle[descriptor.getElementName(2)] as? String)
        ?.let { QuestionCount.valueOf(it) }
        ?: QuestionCount.FIVE

    sealed class SessionState {
        data object Error : SessionState()
        data object Loading : SessionState()
        data class Success(
            val startTime: Instant,
            val currentPage: Int = 0,
            val questionsState: List<QuestionState> = emptyList(),
            val drawReference: Boolean = false,
            val drawHint: Boolean = false,
            val showLeaveDialog: Boolean = false
        ) : SessionState() {
            val currentQuestion: QuestionState
                get() = questionsState[currentPage]
            val isLastQuestion: Boolean
                get() = currentPage + 1 == questionsState.size
        }
    }

    data class QuestionState(
        val question: DictionaryWithGraphic,
        val previousDrawnStrokes: List<List<Offset>> = emptyList(),
    ) {
        val nbStrokeToDraw
            get() = question.graphics.medians.size

        val isAnswered
            get() = previousDrawnStrokes.size == nbStrokeToDraw

    }

    // No need to pass this to the view: out of state
    private val responses = mutableListOf<SessionResponse>()

    private val _state: MutableStateFlow<SessionState> = MutableStateFlow(SessionState.Loading)
    val state = _state.asStateFlow()

    init {
        loadQuestions()
    }

    fun onEvent(event: SessionScreenEvent) {
        when (event) {
            Next -> next()
            Reload -> loadQuestions()
            ToggleLeaveDialog -> toggleLeaveDialog()
            Finish -> finishSession()
            Reset -> resetDrawing()
            is StrokeCompleted -> strokeCompleted(
                stroke = event.stroke,
                referenceStrokes = event.referenceStrokes
            )
        }
    }

    private fun next() {
        _state.update {
            if (it !is SessionState.Success) return
            it.copy(currentPage = it.currentPage + 1)
        }
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            repository.generateSession(levels.toDomain(), limit.value)
                .onSuccess { data ->
                    _state.update {
                        SessionState.Success(
                            questionsState = data.map { q -> QuestionState(q) },
                            drawReference = difficulty != DifficultyLevel.HARD,
                            drawHint = difficulty == DifficultyLevel.EASY,
                            startTime = Clock.System.now()
                        )
                    }
                }.onFailure {
                    _state.update { SessionState.Error }
                }
        }
    }

    private fun toggleLeaveDialog() {
        _state.update {
            if (it !is SessionState.Success) return
            it.copy(showLeaveDialog = !it.showLeaveDialog)
        }
    }

    private fun resetDrawing() {
        updateDrawing(strokes = emptyList())
    }

    private fun strokeCompleted(
        stroke: List<Offset>,
        referenceStrokes: List<List<Offset>>
    ) {
        if (state.value !is SessionState.Success) return
        val s = _state.value as SessionState.Success

        val currentQuestion = s.currentQuestion
        val previousDrawnStrokes = currentQuestion.previousDrawnStrokes

        val newStrokes = previousDrawnStrokes + listOf(stroke)

        if (newStrokes.size == currentQuestion.nbStrokeToDraw) {
            analyzeAndReport(
                graphic = currentQuestion.question.graphics,
                referenceStrokes = referenceStrokes,
                drawnStrokes = newStrokes
            )
        }

        updateDrawing(newStrokes)
    }

    private fun analyzeAndReport(
        graphic: Graphic,
        referenceStrokes: List<List<Offset>>,
        drawnStrokes: List<List<Offset>>
    ) {
        viewModelScope.launch {
            val output = analyzeUserDrawing.calculate(referenceStrokes, drawnStrokes)
            val parsed = drawnStrokes.map { s -> Stroke(s.map { Point(it.x, it.y) }) }
            val sessionResponse = SessionResponse(graphic.code, output, parsed)
            responses.add(sessionResponse)
        }
    }

    private fun updateDrawing(
        strokes: List<List<Offset>>,
    ) {
        _state.update { state ->
            if (state !is SessionState.Success) return
            val s = state
            s.copy(
                questionsState = state.questionsState.mapIndexed { index, questionState ->
                    if (index == state.currentPage) questionState.copy(
                        previousDrawnStrokes = strokes,
                    )
                    else questionState
                }
            )
        }
    }

    private fun finishSession() {
        if (state.value !is SessionState.Success) return
        val s = _state.value as SessionState.Success

        val endTime = Clock.System.now()
        val timeElapsed = endTime - s.startTime

        val dictionary = s.questionsState.map { it.question.dictionary }

        val score = scoreCalculator.calculate(
            questions = dictionary,
            difficulty = difficulty,
            timeElapsed = timeElapsed.inWholeMilliseconds
        )
        viewModelScope.launch {
            val session = Session(
                date = endTime,
                duration = timeElapsed,
                difficulty = difficulty,
                questionsCount = responses.count(),
                score = score,
            )
            sessionRepository.save(session, responses)

            withContext(Dispatchers.Main) {
                AppNavigation.navigate(LearningInternalRoute.CongratulationRoute, true)
            }
        }
    }

    private fun List<FrequencyLevel>.toDomain(): List<CharacterFrequencyLevel> = map {
        when (it) {
            FrequencyLevel.COMMON -> CharacterFrequencyLevel.COMMON
            FrequencyLevel.FREQUENT -> CharacterFrequencyLevel.FREQUENT
            FrequencyLevel.STANDARD -> CharacterFrequencyLevel.STANDARD
        }
    }
}