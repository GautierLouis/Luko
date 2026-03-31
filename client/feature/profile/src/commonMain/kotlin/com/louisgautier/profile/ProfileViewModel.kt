package com.louisgautier.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class ProfileViewModel(): ViewModel() {
    internal data class UiState(
        val empty: String = ""
    )

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()
}