package com.louisgautier.app.main

internal sealed class MainScaffoldEvent {
    data class OnBottomItemClicked(
        val item: BottomNavItem,
    ) : MainScaffoldEvent()
}
