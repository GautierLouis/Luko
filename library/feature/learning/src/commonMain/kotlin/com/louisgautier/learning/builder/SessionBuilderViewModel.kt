package com.louisgautier.learning.builder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louisgautier.designsystem.components.attrs.FrequencyLevel
import com.louisgautier.domain.model.DifficultyLevel
import com.louisgautier.learning.builder.SessionBuilderScreenEvent.OnDifficultySelected
import com.louisgautier.learning.builder.SessionBuilderScreenEvent.OnLevelSelected
import com.louisgautier.learning.builder.SessionBuilderScreenEvent.OnNextPage
import com.louisgautier.learning.builder.SessionBuilderScreenEvent.OnPreviousPage
import com.louisgautier.learning.builder.SessionBuilderScreenEvent.OnQuestionCountSelected
import com.louisgautier.learning.routing.LearningInternalRoute
import com.louisgautier.navigation.AppNavigation
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SessionBuilderViewModel : ViewModel() {

    companion object {
        const val PAGE_COUNT = 2
    }

    internal data class UiState(
        val levels: List<FrequencyLevel> = listOf(FrequencyLevel.COMMON),
        val difficulty: DifficultyLevel = DifficultyLevel.EASY,
        val questionCount: QuestionCount = QuestionCount.FIVE,
        val currentPage: Int = 0
    ) {
        val isFinished = currentPage + 1 == PAGE_COUNT
    }

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    private val _pageEvent = MutableSharedFlow<Int>()
    val pageAction: SharedFlow<Int> = _pageEvent.asSharedFlow()

    fun onEventReceived(event: SessionBuilderScreenEvent) {
        when (event) {
            is OnDifficultySelected -> {
                _state.update { it.copy(difficulty = event.difficulty) }
            }

            is OnLevelSelected -> {
                _state.update {
                    it.copy(
                        levels = if (event.level in it.levels) {
                            it.levels - event.level
                        } else {
                            it.levels + event.level
                        }
                    )
                }
            }

            is OnQuestionCountSelected -> {
                _state.update { it.copy(questionCount = event.questionCount) }
            }

            is OnNextPage -> {
                viewModelScope.launch {
                    if (event.currentPage < event.pageCount - 1) {
                        _state.update { it.copy(currentPage = event.currentPage + 1) }
                        _pageEvent.emit(event.currentPage + 1)
                    } else {
                        onFinish()
                    }
                }
            }

            is OnPreviousPage -> {
                viewModelScope.launch {
                    _state.update { it.copy(currentPage = event.currentPage - 1) }
                    _pageEvent.emit(event.currentPage - 1)
                }
            }
        }
    }

    private fun onFinish() {
        AppNavigation.navigate(
            route = LearningInternalRoute.SessionRoute(
                levels = state.value.levels,
                difficulty = state.value.difficulty,
                limit = state.value.questionCount
            ),
            clearBackStack = true
        )
    }
}