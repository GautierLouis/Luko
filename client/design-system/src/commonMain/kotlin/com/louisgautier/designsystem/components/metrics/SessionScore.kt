package com.louisgautier.designsystem.components.metrics

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun SessionScore(
    score: Int,
    modifier: Modifier = Modifier,
) {
    val color = Theme.colors.orangeFamily.focusRing

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "+",
                color = color,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = score.toString(), // TODO(number) Format large number
                color = color,
                fontWeight = FontWeight.SemiBold,
                style = Theme.typography.titleLarge
            )
        }
        Text(
            text = " XP",
            color = color,
            style = Theme.typography.bodySmall,
            lineHeight = Theme.typography.bodyLarge.lineHeight
        )
    }
}

@Composable
@Preview
fun PreviewSessionScore(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {

    AppThemeWrapper(themeMode) {
        SessionScore(
            score = 2000
        )
    }
}