package xyz.luko.onboarding.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class OnboardingViewModel : ViewModel() {

    data class UIState(
        val dummy: String = ""
    )

    val state: StateFlow<UIState>
        field = MutableStateFlow(UIState())
}
