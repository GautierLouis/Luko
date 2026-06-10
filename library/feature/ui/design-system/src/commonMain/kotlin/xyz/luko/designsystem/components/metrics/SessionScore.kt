package xyz.luko.designsystem.components.metrics

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme

@Composable
fun SessionScore(
    score: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "+",
                color = Theme.materialColors.tertiary,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = score,
                color = Theme.materialColors.tertiary,
                fontWeight = FontWeight.SemiBold,
                style = Theme.typography.titleLarge,
            )
        }
        Text(
            text = " XP",
            color = Theme.materialColors.tertiary,
            style = Theme.typography.bodySmall,
            lineHeight = Theme.typography.bodyLarge.lineHeight,
        )
    }
}

@Preview
@Composable
private fun PreviewSessionScore(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        SessionScore(
            score = "2000",
        )
    }
}
