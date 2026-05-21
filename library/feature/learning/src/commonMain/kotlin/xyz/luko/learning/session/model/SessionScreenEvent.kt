package xyz.luko.learning.session.model

import androidx.compose.ui.geometry.Offset

internal sealed class SessionScreenEvent {

    data object ToggleLeaveDialog : SessionScreenEvent()

    data object Reload : SessionScreenEvent()

    data object Finish : SessionScreenEvent()

    data object Next : SessionScreenEvent()

    data class StrokeCompleted(
        val stroke: List<Offset>,
    ) : SessionScreenEvent()

    data object Reset : SessionScreenEvent()
}
