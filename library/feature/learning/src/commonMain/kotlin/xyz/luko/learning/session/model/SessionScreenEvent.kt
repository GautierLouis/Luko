package xyz.luko.learning.session.model

import xyz.luko.domain.model.Stroke

internal sealed class SessionScreenEvent {

    data object ToggleLeaveDialog : SessionScreenEvent()

    data object Reload : SessionScreenEvent()

    data object Finish : SessionScreenEvent()

    data object Next : SessionScreenEvent()

    data class StrokeCompleted(
        val stroke: Stroke,
    ) : SessionScreenEvent()

    data object Reset : SessionScreenEvent()
}
