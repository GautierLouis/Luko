package xyz.luko.home

sealed class HomeScreenEvent {
    data object StartOnboarding : HomeScreenEvent()
}
