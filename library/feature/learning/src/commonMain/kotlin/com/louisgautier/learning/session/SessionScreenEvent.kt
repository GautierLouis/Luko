package com.louisgautier.learning.session

import androidx.compose.ui.geometry.Offset

internal sealed class SessionScreenEvent() {
    data class StrokeCompleted(
        val stroke: List<Offset>,
        val referenceStrokes: List<List<Offset>>
    ) : SessionScreenEvent()

    data object ToggleLeaveDialog: SessionScreenEvent()
    data object Reset : SessionScreenEvent()
    data object Reload : SessionScreenEvent()
    data object Finish : SessionScreenEvent()
    data object Next : SessionScreenEvent()
}