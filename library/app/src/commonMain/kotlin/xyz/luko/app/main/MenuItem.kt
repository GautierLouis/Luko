package xyz.luko.app.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import xyz.luko.ui.designsystem.icon.AppIcon
import xyz.luko.ui.designsystem.icon.Feed
import xyz.luko.ui.designsystem.icon.Home
import xyz.luko.ui.designsystem.icon.Library
import xyz.luko.ui.designsystem.icon.Person
import xyz.luko.ui.designsystem.icon.PlayArrow
import xyz.luko.ui.designsystem.theme.Theme

internal enum class MenuItem(
    val icon: ImageVector,
) {
    Home(AppIcon.Home),
    Dictionary(AppIcon.Library),
    Feed(AppIcon.Feed),
    Profile(AppIcon.Person),
    Session(AppIcon.PlayArrow),
}

@Composable
internal fun MenuItem.title() =
    when (this) {
        MenuItem.Home -> Theme.strings.home
        MenuItem.Dictionary -> Theme.strings.dictionary
        MenuItem.Feed -> Theme.strings.feed
        MenuItem.Profile -> Theme.strings.profile
        MenuItem.Session -> Theme.strings.practice
    }
