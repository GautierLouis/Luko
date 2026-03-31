package com.louisgautier.designsystem.components.metrics

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.louisgautier.designsystem.theme.Theme

@Composable
internal fun RowScope.MetricHeader(
    title: String,
    icon: ImageVector,
    trailing: @Composable () -> Unit = {},
) {
    RoundIcon(
        icon = icon,
        colorFamily = Theme.colors.tealFamily
    )
    Text(
        text = title,
        style = Theme.typography.titleMedium,
        color = Theme.materialColors.onBackground
    )
    Spacer(Modifier.Companion.weight(1f))
    trailing()
}