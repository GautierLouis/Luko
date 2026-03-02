package com.louisgautier.learning.session

import androidx.compose.ui.geometry.Offset

sealed class SessionEvent() {
    data class StrokeCompleted(
        val stroke: List<Offset>,
        val referenceStrokes: List<List<Offset>>
    ) : SessionEvent()

    data object Reset : SessionEvent()
    data object Finish : SessionEvent()
}