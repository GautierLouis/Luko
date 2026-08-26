package xyz.luko.learning.congratulation.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.domain.model.Session
import xyz.luko.ui.navigation.AppRoute
import kotlin.time.Duration.Companion.milliseconds

internal class CongratulationViewModel(
    params: AppRoute.Learning.Congratulation
) : ViewModel() {

    data class UIState(
        val startAnim: Boolean = false,
        val session: Session,
    )

    val state: StateFlow<UIState>
        field = MutableStateFlow(UIState(session = params.session))

    init {
        viewModelScope.launch {
            delay(300.milliseconds)
            state.update { it.copy(startAnim = true) }
        }
    }
}
