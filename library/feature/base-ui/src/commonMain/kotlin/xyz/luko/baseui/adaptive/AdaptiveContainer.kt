package xyz.luko.baseui.adaptive

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import xyz.luko.designsystem.token.dimens.Spacing

@Composable
fun AdaptiveContainer(
    useRow: Boolean,
    verticalArrangement: Arrangement.Vertical = Spacing.medium,
    horizontalArrangement: Arrangement.Horizontal = Spacing.medium,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit,
) {
    if (useRow) {
        Row(
            modifier = modifier,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment
        ) { content(Modifier.weight(1f)) }
    } else {
        Column(
            modifier = modifier,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) { content(Modifier) }
    }
}
