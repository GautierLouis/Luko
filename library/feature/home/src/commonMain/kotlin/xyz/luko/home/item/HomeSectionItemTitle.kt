package xyz.luko.home.item

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme


@Composable
internal fun HomeSectionItemTitle(
    title: String,
    modifier: Modifier = Modifier,
    action: @Composable () -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = Theme.typography.titleMedium,
        )
        Spacer(Modifier.weight(1f))
        action()
    }
}

@Preview
@Composable
private fun PreviewHomeSectionItemTitle(
    @PreviewParameter(provider = ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        HomeSectionItemTitle("Title")
    }
}
