package xyz.luko.learning.list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.Spacing
import kotlin.math.roundToInt

@Composable
internal fun StrokeItem(
    index: Int,
    value: Float
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Spacing.medium
    ) {
        Text(
            text = "${index.inc()}",
            textAlign = TextAlign.Center,
            modifier = Modifier.width(20.dp)
        )
        LinearProgressIndicator(
            modifier = Modifier.weight(1f),
            color = value.toAccuracyColor().primary,
            trackColor = Theme.materialColors.surfaceContainer,
            progress = { value / 100f },
            drawStopIndicator = {}
        )
        Text(
            text = "${value.roundToInt()}%",
            color = value.toAccuracyColor().primary,
            modifier = Modifier.width(36.dp)
        )
    }
}
