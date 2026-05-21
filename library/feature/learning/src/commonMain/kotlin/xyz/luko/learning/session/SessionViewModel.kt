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
import xyz.luko.baseui.session.toDomain
import xyz.luko.domain.model.DictionaryWithGraphic
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.Point
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.SessionResponse
import xyz.luko.domain.model.Stroke
import xyz.luko.domain.repository.CharacterRepository
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.learning.routing.LearningInternalRoute
import xyz.luko.learning.session.model.DrawingPageState
import xyz.luko.learning.session.model.SessionScreenEvent
import xyz.luko.learning.session.model.SessionScreenEvent.Finish
import xyz.luko.learning.session.model.SessionScreenEvent.Next
import xyz.luko.learning.session.model.SessionScreenEvent.Reload
import xyz.luko.learning.session.model.SessionScreenEvent.ToggleLeaveDialog
import xyz.luko.learning.session.model.SessionState
import xyz.luko.learning.session.model.getParams
import xyz.luko.learning.session.usecase.AccuracyCalculatorUseCase
import xyz.luko.learning.session.usecase.CalculateScoreUseCase
import xyz.luko.navigation.AppNavigation
import kotlin.time.Clock


internal class SessionViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: CharacterRepository,
    private val sessionRepository: SessionRepository,
    private val analyzeUserDrawing: AccuracyCalculatorUseCase,
    private val scoreCalculator: CalculateScoreUseCase,
) : ViewModel() {

    val params = savedStateHandle.getParams()
    val drawHint get() = params.difficulty == DifficultyLevel.EASY
    val drawReference get() = params.difficulty != DifficultyLevel.HARD

    // No need to pass this to the view: out of state
    private val responses = mutableListOf<SessionResponse>()

    private val _state: MutableStateFlow<SessionState> = MutableStateFlow(SessionState.Loading)
    val state = _state.asStateFlow()

    init {
        loadQuestions()
    }

    fun onEvent(event: SessionScreenEvent) = when (event) {
        Reload -> loadQuestions()
        Next -> next()
        ToggleLeaveDialog -> toggleLeaveDialog()
        Finish -> finishSession()
        is SessionScreenEvent.StrokeCompleted -> onStrokeCompleted(event.stroke)
        SessionScreenEvent.Reset -> resetPage()
    }

    // --- Session ---
    private fun loadQuestions() {
        viewModelScope.launch {
            repository
                .generateSession(params.levels.toDomain(), params.limit.value)
                .onSuccess { data ->
                    _state.update {
                        SessionState.Success(
                            startTime = Clock.System.now(),
                            questions = data,
                            drawingPageState = data.toInitialPageState(),
                        )
                    }
                }.onFailure {
                    _state.update { SessionState.Error }
                }
        }
    }

    private fun List<DictionaryWithGraphic>.toInitialPageState() = associate {
        it.dictionary.code to DrawingPageState(
            referenceStrokes = if (drawReference) it.graphics.smoothMedians else emptyList(),
            referenceHint = if (drawHint) it.graphics.smoothMedians.firstOrNull() else null,
        )
    }

    private fun toggleLeaveDialog() = _state.updateSuccess {
        it.copy(showLeaveDialog = !it.showLeaveDialog)
    }

    private fun next() = _state.updateSuccess {
        it.copy(currentPageIndex = it.currentPageIndex + 1)
    }

    private fun finishSession() {
        val success = _state.value as? SessionState.Success ?: return
        val endTime = Clock.System.now()
        val duration = endTime - success.startTime

        viewModelScope.launch {
            sessionRepository.save(
                session = Session(
                    date = endTime,
                    duration = duration,
                    difficulty = params.difficulty,
                    questionsCount = responses.count(),
                    score = scoreCalculator.calculate(
                        questions = success.questions.map { it.dictionary },
                        difficulty = params.difficulty,
                        timeElapsed = duration.inWholeMilliseconds,
                    ),
                ),
                responses = responses,
            )
            withContext(Dispatchers.Main) {
                AppNavigation.navigate(LearningInternalRoute.CongratulationRoute, true)
            }
        }
    }

    // --- Page ---
    private fun onStrokeCompleted(userStroke: List<Offset>) = _state.updateSuccess { current ->
        val newPageState = current.currentDrawingPageState.addStroke(userStroke)
        if (newPageState.isComplete) analyzeAndReport()
        current.withUpdatedPageState(newPageState)
    }

    private fun resetPage() = _state.updateSuccess { current ->
        current.withUpdatedPageState(current.currentDrawingPageState.reset())
    }

    private fun analyzeAndReport() {
        viewModelScope.launch {
            val success = _state.value as? SessionState.Success ?: return@launch
            val graphic = success.currentQuestion.graphics
            val drawnStrokes = success.currentDrawingPageState.userPreviousOffsets
            val reference = graphic.smoothMedians.map { s -> s.points.map { Offset(it.x, it.y) } }

            val statistics = analyzeUserDrawing.calculate(
                reference = reference,
                userStroke = drawnStrokes,
            )

            responses.add(
                SessionResponse(
                    code = graphic.code,
                    statistics = statistics,
                    strokes = drawnStrokes.map { s ->
                        Stroke(s.map {
                            Point.Straight(
                                it.x,
                                it.y
                            )
                        })
                    },
                )
            )
        }
    }

    // -- Utils ---
    private fun DrawingPageState.addStroke(stroke: List<Offset>): DrawingPageState {
        val newOffsets = userPreviousOffsets + listOf(stroke)
        return copy(
            userPreviousOffsets = newOffsets,
            referenceHint = if (drawHint) referenceStrokes.getOrNull(newOffsets.size) else null,
        )
    }

    private fun DrawingPageState.reset() = copy(
        userPreviousOffsets = emptyList(),
        referenceHint = if (drawHint) referenceStrokes.firstOrNull() else null,
    )

    private fun SessionState.Success.withUpdatedPageState(newPageState: DrawingPageState) =
        copy(drawingPageState = drawingPageState + (currentPageCode to newPageState))


    private inline fun MutableStateFlow<SessionState>.updateSuccess(
        function: (SessionState.Success) -> SessionState,
    ) {
        update { current ->
            val success = current as? SessionState.Success ?: return@update current
            function(success)
        }
    }
}
