package com.louisgautier.app.main


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.Feed
import com.louisgautier.designsystem.icon.Home
import com.louisgautier.designsystem.icon.Library
import com.louisgautier.designsystem.icon.Person
import com.louisgautier.designsystem.theme.Theme

internal enum class BottomNavItem(
    val icon: ImageVector
) {
    Home(AppIcon.Home),
    Dictionary(AppIcon.Library),
    Feed(AppIcon.Feed),
    Profile(AppIcon.Person)
}

@Composable
internal fun BottomNavItem.title() = when (this) {
    BottomNavItem.Home -> Theme.strings.home
    BottomNavItem.Dictionary -> Theme.strings.dictionary
    BottomNavItem.Feed -> Theme.strings.feed
    BottomNavItem.Profile -> Theme.strings.profile
}