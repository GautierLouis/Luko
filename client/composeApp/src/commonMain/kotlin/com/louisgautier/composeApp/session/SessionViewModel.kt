package com.louisgautier.composeApp.session

import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louisgautier.apicontracts.dto.CharacterFrequencyLevel
import com.louisgautier.apicontracts.dto.DictionaryWithGraphic
import com.louisgautier.composeApp.AppNavigation
import com.louisgautier.composeApp.Route
import com.louisgautier.domain.model.Difficulty
import com.louisgautier.domain.model.Response
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.repository.CharacterRepository
import com.louisgautier.domain.repository.SessionRepository
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

    data class UIState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val difficulty: Difficulty = Difficulty.EASY,

        val questions: List<DictionaryWithGraphic> = emptyList(),
        val responses: List<Response> = emptyList(),
        val pagerState: PagerState = PagerState { 0 },
    ) {
        val currentQuestion: DictionaryWithGraphic
            get() = questions[pagerState.currentPage]
        val isAnswered
            get() = responses.any { it.code == currentQuestion.dictionary.code }
        val isLastQuestion
            get() = pagerState.canScrollForward
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

    private var _state = MutableStateFlow<UIState>(UIState())
    val state = _state.asStateFlow()

    val startTime = Clock.System.now()

    val scoreCalculator = CalculateScore()

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
                            questions = data,
                            pagerState = PagerState { data.size },
                            difficulty = difficulty
                        )
                    }
                }.onFailure {
                    _state.update { it.copy(isLoading = false, isError = true) }
                }
        }
    }

    fun onComplete(response: Response) {
        _state.update {
            it.copy(responses = it.responses.plus(response))
        }
    }

    fun endSession() {
        val endTime = Clock.System.now()
        val timeElapsed = endTime - startTime

        viewModelScope.launch {
            val session = Session(
                date = endTime,
                duration = timeElapsed,
                difficulty = difficulty,
                responses = state.value.responses,
                score = scoreCalculator.calculate(
                    questions = state.value.questions.map { it.dictionary },
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

