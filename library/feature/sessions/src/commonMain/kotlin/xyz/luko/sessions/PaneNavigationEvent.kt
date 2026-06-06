package xyz.luko.sessions

internal sealed class PaneNavigationEvent {
    data object NavigateBack : PaneNavigationEvent()
    data class NavigateToDetails(val sessionId: Long) : PaneNavigationEvent()
    data class NavigateToExtra(val responseCode: Int) : PaneNavigationEvent()
}
