package xyz.luko.learning.session

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.baseui.session.toDomain
import xyz.luko.domain.model.Dictionary
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.SessionResponse
import xyz.luko.domain.model.Stroke
import xyz.luko.domain.repository.DictionaryRepository
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.learning.congratulation.EndOfSessionCoordinator
import xyz.luko.learning.navigation.LearningInternalRoute
import xyz.luko.learning.session.model.DrawingPageState
import xyz.luko.learning.session.model.SessionScreenEvent
import xyz.luko.learning.session.model.SessionScreenEvent.Finish
import xyz.luko.learning.session.model.SessionScreenEvent.Next
import xyz.luko.learning.session.model.SessionScreenEvent.Reload
import xyz.luko.learning.session.model.SessionScreenEvent.ToggleLeaveDialog
import xyz.luko.learning.session.model.SessionState
import xyz.luko.learning.session.usecase.AccuracyCalculatorUseCase
import xyz.luko.learning.session.usecase.CalculateScoreUseCase
import xyz.luko.tracking.Tracker
import xyz.luko.tracking.TrackingEvent
import xyz.luko.utils.AppConfig
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
internal class SessionViewModel(
    private val params: LearningInternalRoute.SessionRoute,
    private val repository: DictionaryRepository,
    private val sessionRepository: SessionRepository,
    private val analyzeUserDrawing: AccuracyCalculatorUseCase,
    private val scoreCalculator: CalculateScoreUseCase,
    private val coordinator: EndOfSessionCoordinator,
    private val appConfig: AppConfig
) : ViewModel() {

    val drawHint get() = params.difficulty == DifficultyLevel.EASY
    val drawReference get() = params.difficulty != DifficultyLevel.HARD

    // No need to pass this to the view: out of state
    private val responses = mutableListOf<SessionResponse>()

    private val trackingSessionID = Uuid.random().toString()

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
        SessionScreenEvent.AutofillDebug -> autofillSessionForDebug()

    }

    // DEBUG ONLY
    private fun autofillSessionForDebug() {
        val state = (_state.value as SessionState.Success)
        state.drawingPageState.forEach { (key, value) ->
            val reference = value.referenceStrokes.map { s -> s.points.map { Offset(it.x, it.y) } }
            val statistics = analyzeUserDrawing.calculate(
                reference = reference,
                userStroke = reference,
            )
            responses.add(
                SessionResponse(
                    code = key,
                    pinyin = state.questions.first { it.code == key }.pinyin.firstOrNull()
                        .orEmpty(),
                    statistics = statistics,
                    references = value.referenceStrokes,
                    strokes = value.referenceStrokes,
                )
            )
        }
        finishSession()
    }

    // --- Session ---
    private fun loadQuestions() {
        viewModelScope.launch {
            repository
                .createSession(params.levels.toDomain(), params.limit.value)
                .onSuccess { data ->
                    val now = Clock.System.now()

                    TrackingEvent.CreateSession(
                        trackingId = trackingSessionID,
                        startDate = now.toString(),
                        difficulty = params.difficulty.name,
                        levels = params.levels.joinToString(),
                        questions = data.map { it.code },
                    ).run { Tracker.track(this) }

                    _state.update {
                        SessionState.Success(
                            startTime = now,
                            questions = data,
                            showDebugMenu = !appConfig.isProduction,
                            drawingPageState = data.toInitialPageState(),
                        )
                    }
                }.onFailure {
                    _state.update { SessionState.Error }
                }
        }
    }

    private fun List<Dictionary>.toInitialPageState() = associate {
        it.code to DrawingPageState(
            totalStrokes = it.medians.size,
            referenceStrokes = if (drawReference) it.medians else emptyList(),
            referenceHint = if (drawHint) it.medians.firstOrNull() else null,
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
        val score = scoreCalculator.calculate(
            questions = success.questions,
            difficulty = params.difficulty,
            timeElapsed = duration.inWholeMilliseconds,
        )

        viewModelScope.launch {
            TrackingEvent.SessionFinish(
                trackingId = trackingSessionID,
                endDate = endTime.toString(),
                duration = duration.inWholeMilliseconds,
                difficulty = params.difficulty.name,
                levels = params.levels.joinToString(),
                score = score,
                responses = responses.associate { it.code to it.statistics.overallAccuracy }
            ).run { Tracker.track(this) }

            sessionRepository.save(
                session = Session(
                    date = endTime,
                    duration = duration,
                    difficulty = params.difficulty,
                    questionsCount = responses.count(),
                    score = score,
                    accuracy = responses.map { it.statistics.overallAccuracy }.average()
                ),
                responses = responses,
            )
            coordinator.prepareAndStart()
        }
    }

    // --- Page ---
    private fun onStrokeCompleted(userStroke: Stroke) {
        _state.updateSuccess { current ->
            val newPageState = current.currentDrawingPageState.addStroke(userStroke)
            current.withUpdatedPageState(newPageState)
        }
        val isComplete = (_state.value as SessionState.Success).currentDrawingPageState.isComplete
        if (isComplete) analyzeAndReport()
    }

    private fun resetPage() = _state.updateSuccess { current ->
        current.withUpdatedPageState(current.currentDrawingPageState.reset())
    }

    private fun analyzeAndReport() {
        viewModelScope.launch {
            val success = _state.value as? SessionState.Success ?: return@launch
            val medians = success.currentQuestion.medians
            val drawnStrokes = success.currentDrawingPageState.userPreviousOffsets
            val reference = medians.map { s -> s.points.map { Offset(it.x, it.y) } }
            val drawing = drawnStrokes.map { s -> s.points.map { Offset(it.x, it.y) } }

            val statistics = analyzeUserDrawing.calculate(
                reference = reference,
                userStroke = drawing,
            )

            responses.add(
                SessionResponse(
                    code = success.currentQuestion.code,
                    pinyin = success.pinyin,
                    statistics = statistics,
                    references = medians,
                    strokes = drawnStrokes,
                )
            )
        }
    }

    // -- Utils ---
    private fun DrawingPageState.addStroke(stroke: Stroke): DrawingPageState {
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
