package com.louisgautier.composeApp.main

sealed class MainScreenEvent {
    data class OnBottomItemClicked(val item: BottomNavItem) : MainScreenEvent()
}