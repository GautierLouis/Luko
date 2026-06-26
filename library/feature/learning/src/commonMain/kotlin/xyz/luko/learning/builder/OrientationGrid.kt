package xyz.luko.learning.builder

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.luko.ui.core.window.rememberWindowInfo
import xyz.luko.ui.designsystem.token.dimens.Spacing

@Composable
internal fun <T> OrientationGrid(
    data: List<T>,
    key: (T) -> Any = {},
    modifier: Modifier = Modifier.Companion,
    content: @Composable LazyGridItemScope.(T) -> Unit
) {

    val windowInfo = rememberWindowInfo()

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(if (windowInfo.isHeightCompact()) data.size else 1),
        userScrollEnabled = false,
        verticalArrangement = Spacing.large,
        horizontalArrangement = Spacing.large,
    ) {

        items(
            count = data.size,
            key = { index -> key(data[index]) }
        ) { index ->
            content(data[index])
        }
    }
}
