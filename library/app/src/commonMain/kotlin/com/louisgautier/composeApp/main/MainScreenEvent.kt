package com.louisgautier.composeApp.main

internal sealed class MainScreenEvent {
    data class OnBottomItemClicked(val item: BottomNavItem) : MainScreenEvent()
}