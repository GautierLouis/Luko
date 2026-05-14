package xyz.luko.learning.builder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.RoundedBolt
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme

@Composable
fun LevelIconSelectable(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Unspecified,
    contentColor: Color = Color.Unspecified,
) {
    Box(
        modifier
            .padding(top = 2.dp)
            .size(36.dp)
            .background(containerColor, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier,
            tint = contentColor
        )
    }
}

@Preview
@Composable
private fun PreviewLevelIconSelectable(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            LevelIconSelectable(
                icon = AppIcon.RoundedBolt
            )
        }
    }
}