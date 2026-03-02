package com.louisgautier.learning.session

import androidx.compose.foundation.pager.PagerState
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louisgautier.domain.model.CharacterFrequencyLevel
import com.louisgautier.domain.model.DictionaryWithGraphic
import com.louisgautier.domain.model.Difficulty
import com.louisgautier.domain.model.Graphic
import com.louisgautier.domain.model.Point
import com.louisgautier.domain.model.Response
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.model.Stroke
import com.louisgautier.domain.repository.CharacterRepository
import com.louisgautier.domain.repository.SessionRepository
import com.louisgautier.learning.CalculateScore
import com.louisgautier.learning.builder.QuestionCount
import com.louisgautier.learning.drawing.analyzeUserDrawing
import com.louisgautier.utils.AppNavigation
import com.louisgautier.utils.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
class SessionViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: CharacterRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    // Related to current drawing
    data class GraphicSketcherState(
        val ongoingStrokeIndex: Int = 0,
        val previousDrawnStrokes: List<List<Offset>> = emptyList(),
        val drawnStroke: List<Offset> = emptyList(),
    )

    // Related to current Question
    data class QuestionState(
        val question: DictionaryWithGraphic,
        val sketcherState: GraphicSketcherState = GraphicSketcherState(),
        val isAnswered: Boolean = false,
    )

    // Related to current Session
    data class UIState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val questionStates: List<QuestionState> = emptyList(),
        val pagerState: PagerState = PagerState { 0 },
        val drawReference: Boolean = false,
        val drawHint: Boolean = false
    ) {
        val currentQuestion: QuestionState
            get() = questionStates[pagerState.currentPage]
        val isLastQuestion
            get() = pagerState.canScrollForward
        val isAnswered
            get() = currentQuestion.isAnswered
    }

    private val level: List<CharacterFrequencyLevel> = (savedStateHandle["levels"] as? String)
        ?.split(",")
        ?.map { CharacterFrequencyLevel.valueOf(it.trim()) }
        ?: CharacterFrequencyLevel.entries
    private val difficulty: Difficulty = (savedStateHandle["difficulty"] as? String)
        ?.let { Difficulty.valueOf(it) }
        ?: Difficulty.EASY
    private val limit: QuestionCount = (savedStateHandle["limit"] as? String)
        ?.let { QuestionCount.valueOf(it) }
        ?: QuestionCount.FIVE

    private val _state = MutableStateFlow(UIState())
    val state = _state.asStateFlow()

    // No need to pass this to the view: out of state
    private val responses = mutableListOf<Response>()
    private val startTime = Clock.System.now()
    private val scoreCalculator = CalculateScore()

    init {
        loadQuestions()
    }

    fun loadQuestions() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.generateSession(level, limit.value)
                .onSuccess { data ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isError = false,
                            questionStates = data.map { q -> QuestionState(q) },
                            drawReference = difficulty != Difficulty.HARD,
                            drawHint = difficulty == Difficulty.EASY,
                            pagerState = PagerState { data.size },
                        )
                    }
                }.onFailure {
                    _state.update { it.copy(isLoading = false, isError = true) }
                }
        }
    }

    // --- Sketcher ---

    fun onSketcherEvent(event: SessionEvent) {
        when (event) {
            SessionEvent.Finish -> endSession()
            SessionEvent.Reset -> updateSketcherState { GraphicSketcherState() }
            is SessionEvent.StrokeCompleted -> updateSketcherState { current ->
                val newStrokes = current.previousDrawnStrokes + listOf(event.stroke)
                val newIndex = current.ongoingStrokeIndex + 1

                if (newIndex == _state.value.currentQuestion.question.graphics.medians.size) {
                    analyzeAndReport(
                        graphic = _state.value.currentQuestion.question.graphics,
                        referenceStrokes = event.referenceStrokes,
                        drawnStrokes = newStrokes
                    )
                }

                current.copy(
                    previousDrawnStrokes = newStrokes,
                    ongoingStrokeIndex = newIndex,
                    drawnStroke = emptyList()
                )
            }
        }
    }

    private fun updateSketcherState(update: (GraphicSketcherState) -> GraphicSketcherState) {
        _state.update { state ->
            state.copy(questionStates = state.questionStates.mapIndexed { index, questionState ->
                if (index == state.pagerState.currentPage) questionState.copy(
                    sketcherState = update(
                        questionState.sketcherState
                    )
                )
                else questionState
            })
        }
    }

    private fun analyzeAndReport(
        graphic: Graphic,
        referenceStrokes: List<List<Offset>>,
        drawnStrokes: List<List<Offset>>
    ) {
        viewModelScope.launch {
            val output = analyzeUserDrawing(referenceStrokes, drawnStrokes)
            val parsed = drawnStrokes.map { s -> Stroke(s.map { Point(it.x, it.y) }) }
            val response = Response(graphic.code, output, parsed)
            responses.add(response)
            _state.update { state ->
                state.copy(questionStates = state.questionStates.map {
                    if (it.question.dictionary.code == response.code) it.copy(isAnswered = true)
                    else it
                })
            }
        }
    }

    // --- Session ---

    fun endSession() {
        val endTime = Clock.System.now()
        val timeElapsed = endTime - startTime

        viewModelScope.launch {
            val session = Session(
                date = endTime,
                duration = timeElapsed,
                difficulty = difficulty,
                responses = responses,
                score = scoreCalculator.calculate(
                    questions = state.value.questionStates.map { it.question.dictionary },
                    difficulty = difficulty,
                    timeElapsed = timeElapsed.inWholeMilliseconds
                ),
            )
            sessionRepository.save(session)

            withContext(Dispatchers.Main) {
                AppNavigation.navigate(Route.SessionCongratulationScreenRoute, true)
            }
        }
    }
}

