package xyz.luko.learning.congratulation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

internal class StreakRefreshViewModel : ViewModel() {
    data class UIState(
        val showNew: Boolean = false,
        val showBtn: Boolean = false
    )

    private val _state = MutableStateFlow<UIState>(UIState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            delay(600.milliseconds)
            _state.update { it.copy(showNew = true) }
            delay(600.milliseconds)
            _state.update { it.copy(showBtn = true) }
        }
    }
}
