package com.louisgautier.composeApp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.louisgautier.composeApp.home.HomeScreen
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.dictionary.DictionaryScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = Theme.materialColors.background,
        bottomBar = {
            BottomBar(
                items = state.bottomNavItem,
                selectedItem = state.selectedItem,
                onClick = { viewModel.updateBottomItem(it) }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (state.selectedItem) {
                BottomNavItem.Home -> HomeScreen()
                BottomNavItem.Dictionary -> DictionaryScreen()
                BottomNavItem.Profile -> ProfileScreen()
            }
        }
    }
}

@Composable
fun ProfileScreen() {

}