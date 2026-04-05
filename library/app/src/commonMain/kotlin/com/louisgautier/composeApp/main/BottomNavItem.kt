package com.louisgautier.composeApp.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.louisgautier.designsystem.theme.Theme

internal enum class BottomNavItem(
    val icon: ImageVector
) {
    Home(Icons.Default.Home),
    Dictionary(Icons.Default.LocalLibrary),
    Profile(Icons.Default.Person)
}

@Composable
internal fun BottomNavItem.title() = when (this) {
    BottomNavItem.Home -> Theme.strings.home
    BottomNavItem.Dictionary -> Theme.strings.dictionary
    BottomNavItem.Profile -> Theme.strings.profile
}