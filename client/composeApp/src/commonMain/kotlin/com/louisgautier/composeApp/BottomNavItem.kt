package com.louisgautier.composeApp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.louisgautier.designsystem.theme.Theme
import kotlinx.collections.immutable.immutableListOf
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

enum class BottomNavItem(
    val icon: ImageVector
) {
    Home(Icons.Default.Home),
    Dictionary(Icons.Default.LocalLibrary),
    Profile(Icons.Default.Person)
}

@Composable
fun BottomNavItem.title()  = when(this) {
    BottomNavItem.Home -> Theme.strings.home
    BottomNavItem.Dictionary -> Theme.strings.dictionary
    BottomNavItem.Profile -> Theme.strings.profile
}