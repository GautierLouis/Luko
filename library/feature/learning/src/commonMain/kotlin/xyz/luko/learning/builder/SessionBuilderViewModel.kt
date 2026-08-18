package xyz.luko.learning.builder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.firebase.RemoteConfigManager
import xyz.luko.learning.builder.SessionBuilderScreenEvent.OnDifficultySelected
import xyz.luko.learning.builder.SessionBuilderScreenEvent.OnFrequencySelected
import xyz.luko.learning.navigation.LearningInternalRoute
import xyz.luko.ui.designsystem.components.attrs.FrequencyLevel
import xyz.luko.ui.navigation.AppNavigation

internal class SessionBuilderViewModel(
    private val remoteConfigManager: RemoteConfigManager,
) : ViewModel() {
    companion object {
        const val PAGE_COUNT = 3
    }

    internal data class UiState(
        val levels: List<FrequencyLevel> = listOf(FrequencyLevel.COMMON),
        val difficulty: DifficultyLevel = DifficultyLevel.EASY,
        val questionCount: QuestionCount = QuestionCount.FIVE,
        val currentPage: Int = 0,
        val useAlternativeLayout: Boolean = true,
    ) {
        val isFinished = currentPage + 1 == PAGE_COUNT
        val showPreviewButton = currentPage > 0
        val canDecrease = questionCount.ordinal > 0
        val canIncrease = questionCount.ordinal < QuestionCount.entries.size - 1
    }

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            remoteConfigManager.flags
                .collect { flags ->
                    _state.update { it.copy(useAlternativeLayout = flags.useAlternativeBuilder) }
                }
        }
    }

    fun onEventReceived(event: SessionBuilderScreenEvent) {
        when (event) {
            is OnDifficultySelected -> {
                _state.update { it.copy(difficulty = event.difficulty) }
            }

            is OnFrequencySelected -> {
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

            is SessionBuilderScreenEvent.OnQuestionCountSelected -> {
                _state.update { it.copy(questionCount = event.questionCount) }
            }

            is SessionBuilderScreenEvent.OnNextPage -> {
                if (event.currentPage < event.pageCount - 1) {
                    updatePageState(event.currentPage + 1)
                } else {
                    onFinish()
                }
            }

            is SessionBuilderScreenEvent.OnPreviousPage -> {
                updatePageState(event.currentPage - 1)
            }

            SessionBuilderScreenEvent.QuestionCountDecrease -> {
                _state.update { it.copy(questionCount = it.questionCount.shifted(-1)) }
            }

            SessionBuilderScreenEvent.QuestionCountIncrease -> {
                _state.update { it.copy(questionCount = it.questionCount.shifted(1)) }
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
