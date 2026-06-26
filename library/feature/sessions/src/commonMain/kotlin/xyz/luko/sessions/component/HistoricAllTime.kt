package xyz.luko.sessions.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.designsystem.token.dimens.Padding
import xyz.luko.ui.designsystem.token.dimens.ShapeDefaults

@Composable
internal fun RowScope.HistoricAllTime(
    value: String,
    label: String,
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(2f / 1.5f)
            .background(
                color = Theme.materialColors.surfaceContainer,
                shape = ShapeDefaults.card()
            ).padding(
                horizontal = Padding.extraLarge,
                vertical = Padding.medium
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = value,
            color = Theme.materialColors.onSecondaryContainer,
            style = Theme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            color = Theme.materialColors.onSecondaryContainer,
            style = Theme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}
