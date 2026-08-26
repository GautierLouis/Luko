package xyz.luko.learning.congratulation.streak

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.learning.congratulation.EndOfSessionCoordinator
import kotlin.time.Duration.Companion.milliseconds

internal class StreakRefreshViewModel(
    private val coordinator: EndOfSessionCoordinator,
    private val streakUseCase: GetWeekStreakUseCase,
) : ViewModel() {

    data class UIState(
        val startFirstAnim: Boolean = false,
        val startSecondAnim: Boolean = false,
        val streakDays: ImmutableList<DayStreak> = persistentListOf(),
    )

    val state: StateFlow<UIState>
        field = MutableStateFlow<UIState>(UIState())

    init {
        //TODO Fix anim restart on rotation
        viewModelScope.launch {
            val streak = streakUseCase.invoke()

            state.update { it.copy(streakDays = streak.toImmutableList()) }
            delay(600.milliseconds)
            state.update { it.copy(startFirstAnim = true) }
            delay(600.milliseconds)
            state.update { it.copy(startSecondAnim = true) }
        }
    }

    fun next() {
        coordinator.next()
    }
}
