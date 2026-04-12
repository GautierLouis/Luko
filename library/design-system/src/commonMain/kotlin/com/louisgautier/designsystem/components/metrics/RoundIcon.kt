package com.louisgautier.designsystem.components.metrics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.components.metrics.attrs.RoundIconSize
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedBarChart
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.color.model.LevelColors

@Composable
internal fun RoundIcon(
    icon: ImageVector,
    colors: LevelColors,
    size: RoundIconSize = RoundIconSize.Small,
    modifier: Modifier = Modifier,
) {
    val iconSize = when (size) {
        RoundIconSize.Small -> 24.dp
        RoundIconSize.Large -> 36.dp
    }
    val padding = when (size) {
        RoundIconSize.Small -> 4.dp
        RoundIconSize.Large -> 8.dp
    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colors.onSubtle,
            modifier = Modifier
                .size(iconSize)
                .background(colors.subtle, CircleShape)
                .padding(padding)
        )
    }
}

@Preview
@Composable
private fun PreviewRoundIcon(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        Row {
            RoundIcon(
                icon = AppIcon.RoundedBarChart,
                colors = Theme.appLevelColors.totalScore,
                size = RoundIconSize.Large,
                modifier = Modifier
            )
            RoundIcon(
                icon = AppIcon.RoundedBarChart,
                colors = Theme.appLevelColors.totalScore,
                size = RoundIconSize.Small,
                modifier = Modifier
            )
        }
    }
}