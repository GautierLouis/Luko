package xyz.luko.app.main

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import xyz.luko.ui.core.window.rememberIsWiderThanTall
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
    val isWider = rememberIsWiderThanTall()

    val boxAlignment = when {
        isWider -> Alignment.CenterEnd
        else -> Alignment.BottomCenter
    }

    val orientation = when {
        isWider -> Orientation.Vertical
        else -> Orientation.Horizontal
    }

    val paddingToEdge = when {
        isWider -> PaddingValues(end = Padding.large)
        else -> PaddingValues(bottom = Padding.large)
    }

    val paddingToMenu = when {
        isWider -> PaddingValues(end = MenuDefault.FloatingActionSize + Padding.large)
        else -> PaddingValues(bottom = MenuDefault.FloatingActionSize + Padding.large)
    }

    return remember(isWider) {
        MenuLayout(
            boxAlignment = boxAlignment,
            orientation = orientation,
            paddingToEdge = paddingToEdge,
            paddingToMenu = paddingToMenu,
        )
    }
}
