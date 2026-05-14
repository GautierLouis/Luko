package xyz.luko.app.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.Feed
import xyz.luko.designsystem.icon.Home
import xyz.luko.designsystem.icon.Library
import xyz.luko.designsystem.icon.Person
import xyz.luko.designsystem.theme.Theme

internal enum class BottomNavItem(
    val icon: ImageVector,
) {
    Home(AppIcon.Home),
    Dictionary(AppIcon.Library),
    Feed(AppIcon.Feed),
    Profile(AppIcon.Person),
}

@Composable
internal fun BottomNavItem.title() =
    when (this) {
        BottomNavItem.Home -> Theme.strings.home
        BottomNavItem.Dictionary -> Theme.strings.dictionary
        BottomNavItem.Feed -> Theme.strings.feed
        BottomNavItem.Profile -> Theme.strings.profile
    }
