package xyz.luko.learning.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.domain.model.Dictionary
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.Stroke
import xyz.luko.domain.model.TemporaryResponse
import xyz.luko.domain.model.TemporarySession
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.learning.session.model.DrawingPageState
import xyz.luko.learning.session.model.SessionScreenEvent
import xyz.luko.learning.session.model.SessionScreenEvent.Finish
import xyz.luko.learning.session.model.SessionScreenEvent.Next
import xyz.luko.learning.session.model.SessionScreenEvent.Reload
import xyz.luko.learning.session.model.SessionScreenEvent.ToggleLeaveDialog
import xyz.luko.learning.session.model.SessionState
import xyz.luko.recognition.RecognitionResult
import xyz.luko.tracking.Tracker
import xyz.luko.tracking.TrackingEvent
import xyz.luko.ui.navigation.AppRoute
import xyz.luko.utils.AppConfig
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
internal class SessionViewModel(
    private val params: AppRoute.Learning.StartSession,
    private val repository: SessionRepository,
    private val appConfig: AppConfig,
    private val recognizer: CharacterRecognizedUseCase,
    private val endOfSessionUseCase: EndOfSessionUseCase,
) : ViewModel() {

    val drawHint get() = params.settings.difficultyLevel == DifficultyLevel.EASY
    val drawReference get() = params.settings.difficultyLevel != DifficultyLevel.HARD

    // No need to pass this to the view: out of state
    private val responses = mutableListOf<TemporaryResponse>()

    private val trackingSessionID = Uuid.random().toString()

    val state: StateFlow<SessionState>
        field = MutableStateFlow<SessionState>(SessionState.Loading)

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
        val state = (state.value as SessionState.Success)
        state.drawingPageState.forEach { (key, value) ->
            responses.add(
                TemporaryResponse(
                    code = key,
                    pinyin = state.questions.first { it.code == key }.pinyin
                        .firstOrNull()
                        .orEmpty(),
                    references = value.referenceStrokes,
                    strokes = value.referenceStrokes,
                    recognitionResult = RecognitionResult.SUCCESS.name,
                    difficultyLevel = params.settings.difficultyLevel,
                )
            )
        }
        finishSession()
    }

    // --- Session ---
    private fun loadQuestions() {
        viewModelScope.launch {
            repository
                .createSession(params.settings.frequencyLevel, params.settings.count)
                .onSuccess { data ->
                    val now = Clock.System.now()

                    TrackingEvent.CreateSession(
                        trackingId = trackingSessionID,
                        startDate = now.toString(),
                        difficulty = params.settings.difficultyLevel.name,
                        levels = params.settings.frequencyLevel.joinToString(),
                        questions = data.map { it.code },
                    ).run { Tracker.track(this) }

                    state.update {
                        SessionState.Success(
                            startTime = now,
                            questions = data,
                            showDebugMenu = !appConfig.isProduction,
                            drawingPageState = data.toInitialPageState(),
                        )
                    }
                }.onFailure {
                    state.update { SessionState.Error }
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

    private fun toggleLeaveDialog() = state.updateSuccess {
        it.copy(showLeaveDialog = !it.showLeaveDialog)
    }

    private fun next() = state.updateSuccess {
        it.copy(currentPageIndex = it.currentPageIndex + 1)
    }

    private fun finishSession() {
        val success = state.value as? SessionState.Success ?: return
        val endTime = Clock.System.now()
        val duration = endTime - success.startTime

        TrackingEvent.SessionFinish(
            trackingId = trackingSessionID,
            endDate = endTime.toString(),
            duration = duration.inWholeMilliseconds,
            difficulty = params.settings.difficultyLevel.name,
            levels = params.settings.frequencyLevel.joinToString(),
            responses = emptyMap()
        ).run { Tracker.track(this) }

        viewModelScope.launch {

            val session = TemporarySession(
                date = endTime,
                duration = duration,
                difficulty = params.settings.difficultyLevel,
                questionsCount = responses.count(),
            )

            endOfSessionUseCase.prepare(session, responses, params.settings)
        }
    }

    // --- Page ---
    private fun onStrokeCompleted(userStroke: Stroke) {
        state.updateSuccess { current ->
            val newPageState = current.currentDrawingPageState.addStroke(userStroke)
            current.withUpdatedPageState(newPageState)
        }
        val isComplete = (state.value as SessionState.Success).currentDrawingPageState.isComplete
        if (isComplete) analyzeAndReport()
    }

    private fun resetPage() = state.updateSuccess { current ->
        current.withUpdatedPageState(current.currentDrawingPageState.reset())
    }

    private fun analyzeAndReport() {
        viewModelScope.launch {
            val success = state.value as? SessionState.Success ?: return@launch
            val medians = success.currentQuestion.medians
            val drawnStrokes = success.currentDrawingPageState.userPreviousOffsets

            val result = recognizer.recognize(
                expectedCharacter = Char(success.currentQuestion.code).toString(),
                strokes = drawnStrokes
            )

            responses.add(
                TemporaryResponse(
                    code = success.currentQuestion.code,
                    pinyin = success.pinyin,
                    references = medians,
                    strokes = drawnStrokes,
                    recognitionResult = result.name,
                    difficultyLevel = params.settings.difficultyLevel,
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
