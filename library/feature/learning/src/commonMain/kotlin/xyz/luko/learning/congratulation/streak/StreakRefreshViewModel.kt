package xyz.luko.learning.congratulation.streak

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.learning.congratulation.EndOfSessionCoordinator
import xyz.luko.learning.congratulation.streak.ui.StreakDay
import kotlin.time.Clock
import kotlin.time.Duration.Companion.milliseconds


internal class StreakRefreshViewModel(
    private val coordinator: EndOfSessionCoordinator,
    private val sessionRepository: SessionRepository,
    private val useCase: StreakListUseCase,
) : ViewModel() {

    data class UIState(
        val startFirstAnim: Boolean = false,
        val startSecondAnim: Boolean = false,
        val streakDays: ImmutableList<StreakDay> = persistentListOf(),
    )

    private val _state = MutableStateFlow<UIState>(UIState())
    val state = _state.asStateFlow()

    init {
        //TODO Fix anim restart on rotation
        viewModelScope.launch {
            val streak = useCase.build(getDates())

            _state.update { it.copy(streakDays = streak) }
            delay(600.milliseconds)
            _state.update { it.copy(startFirstAnim = true) }
            delay(600.milliseconds)
            _state.update { it.copy(startSecondAnim = true) }
        }
    }

    private suspend fun getDates(): List<LocalDate> {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

        val monday = today
            .minus(today.dayOfWeek.ordinal, DateTimeUnit.DAY)

        val weekStart = monday
            .atStartOfDayIn(TimeZone.currentSystemDefault())

        val weekEnd = monday
            .plus(7, DateTimeUnit.DAY)
            .atStartOfDayIn(TimeZone.currentSystemDefault())

        return sessionRepository.getSessionDatesForWeek(weekStart, weekEnd)
    }

    fun next() {
        coordinator.next()
    }
}
