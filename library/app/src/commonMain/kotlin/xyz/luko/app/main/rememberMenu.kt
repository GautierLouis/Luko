package xyz.luko.app.main

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import xyz.luko.ui.core.window.rememberWindowInfo
import xyz.luko.ui.designsystem.token.dimens.Padding

internal data class MenuLayout(
    val boxAlignment: Alignment,
    val orientation: Orientation,
    val paddingToEdge: PaddingValues,
    val paddingToMenu: PaddingValues,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun rememberMenu(): MenuLayout {
    val windowInfo = rememberWindowInfo()

    val boxAlignment = when {
        windowInfo.isCompact() -> Alignment.BottomCenter
        else -> Alignment.CenterEnd
    }

    val orientation = when {
        windowInfo.isCompact() -> Orientation.Horizontal
        else -> Orientation.Vertical
    }

    val paddingToEdge = when {
        windowInfo.isCompact() -> PaddingValues(bottom = Padding.large)
        else -> PaddingValues(end = Padding.large)
    }

    val paddingToMenu = when {
        windowInfo.isCompact() -> PaddingValues(bottom = MenuDefault.FloatingActionSize + Padding.large)
        else -> PaddingValues(end = MenuDefault.FloatingActionSize + Padding.large)
    }

    return remember(windowInfo) {
        MenuLayout(
            boxAlignment = boxAlignment,
            orientation = orientation,
            paddingToEdge = paddingToEdge,
            paddingToMenu = paddingToMenu,
        )
    }
}
