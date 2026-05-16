package xyz.luko.learning.builder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.designsystem.components.attrs.FrequencyLevel
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.learning.builder.SessionBuilderScreenEvent.OnDifficultySelected
import xyz.luko.learning.builder.SessionBuilderScreenEvent.OnLevelSelected
import xyz.luko.learning.builder.SessionBuilderScreenEvent.OnNextPage
import xyz.luko.learning.builder.SessionBuilderScreenEvent.OnPreviousPage
import xyz.luko.learning.builder.SessionBuilderScreenEvent.OnQuestionCountSelected
import xyz.luko.learning.routing.LearningInternalRoute
import xyz.luko.navigation.AppNavigation

internal class SessionBuilderViewModel : ViewModel() {
    companion object {
        const val PAGE_COUNT = 3
    }

    internal data class UiState(
        val levels: List<FrequencyLevel> = listOf(FrequencyLevel.COMMON),
        val difficulty: DifficultyLevel = DifficultyLevel.EASY,
        val questionCount: QuestionCount = QuestionCount.FIVE,
        val currentPage: Int = 0,
    ) {
        val isFinished = currentPage + 1 == PAGE_COUNT
        val showPreviewButton = currentPage > 0
    }

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    fun onEventReceived(event: SessionBuilderScreenEvent) {
        when (event) {
            is OnDifficultySelected -> {
                _state.update { it.copy(difficulty = event.difficulty) }
            }

            is OnLevelSelected -> {
                _state.update {
                    it.copy(
                        levels =
                            if (event.level in it.levels) {
                                it.levels - event.level
                            } else {
                                it.levels + event.level
                            },
                    )
                }
            }

            is OnQuestionCountSelected -> {
                _state.update { it.copy(questionCount = event.questionCount) }
            }

            is OnNextPage -> {
                if (event.currentPage < event.pageCount - 1) {
                    updatePageState(event.currentPage + 1)
                } else {
                    onFinish()
                }
            }

            is OnPreviousPage -> {
                updatePageState(event.currentPage - 1)
            }
        }
    }

    private fun updatePageState(page: Int) {
        viewModelScope.launch {
            _state.update { it.copy(currentPage = page) }
        }
    }

    private fun onFinish() {
        AppNavigation.navigate(
            route =
                LearningInternalRoute.SessionRoute(
                    levels = state.value.levels,
                    difficulty = state.value.difficulty,
                    limit = state.value.questionCount,
                ),
            clearBackStack = true,
        )
    }
}
