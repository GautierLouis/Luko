package xyz.luko.learning.builder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.SessionSettings
import xyz.luko.firebase.RemoteConfigManager
import xyz.luko.learning.builder.SessionBuilderScreenEvent.OnDifficultySelected
import xyz.luko.learning.builder.SessionBuilderScreenEvent.OnFrequencySelected
import xyz.luko.ui.designsystem.components.attrs.FrequencyLevel
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute

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

    val state: StateFlow<UiState>
        field = MutableStateFlow(UiState())

    init {
        viewModelScope.launch {
            remoteConfigManager.flags
                .collect { flags ->
                    state.update { it.copy(useAlternativeLayout = flags.useAlternativeBuilder) }
                }
        }
    }

    fun onEventReceived(event: SessionBuilderScreenEvent) {
        when (event) {
            is OnDifficultySelected -> {
                state.update { it.copy(difficulty = event.difficulty) }
            }

            is OnFrequencySelected -> {
                state.update {
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
                state.update { it.copy(questionCount = event.questionCount) }
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
                state.update { it.copy(questionCount = it.questionCount.shifted(-1)) }
            }

            SessionBuilderScreenEvent.QuestionCountIncrease -> {
                state.update { it.copy(questionCount = it.questionCount.shifted(1)) }
            }
        }
    }

    private fun updatePageState(page: Int) {
        viewModelScope.launch {
            state.update { it.copy(currentPage = page) }
        }
    }

    private fun onFinish() {
        AppNavigation.navigate(
            route =
                AppRoute.Learning.StartSession(
                    settings = SessionSettings(
                        frequencyLevel = state.value.levels.map { it.toDomain() },
                        difficultyLevel = state.value.difficulty,
                        count = state.value.questionCount.value,
                    )
                ),
            clearBackStack = true,
        )
    }

    private fun FrequencyLevel.toDomain() = when (this) {
        FrequencyLevel.COMMON -> CharacterFrequencyLevel.COMMON
        FrequencyLevel.FREQUENT -> CharacterFrequencyLevel.FREQUENT
        FrequencyLevel.STANDARD -> CharacterFrequencyLevel.STANDARD
    }
}
