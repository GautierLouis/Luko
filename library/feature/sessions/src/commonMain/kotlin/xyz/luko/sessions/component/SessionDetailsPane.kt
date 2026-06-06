package xyz.luko.sessions.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.Spacing
import xyz.luko.domain.model.SessionResponse
import xyz.luko.ui.core.preview.PreviewProvider

@Composable
internal fun SessionDetailsPane(
    responses: List<SessionResponse>,
    onClick: (Int) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Padding.large)
            .padding(horizontal = Padding.large),
        verticalArrangement = Spacing.large,
    ) {
        items(
            items = responses,
            key = { it.code },
        ) {
            ResponseItem(it) {
                onClick(it.code)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSessionDetailsPane(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        SessionDetailsPane(
            responses = PreviewProvider.sessionResponse
        )
    }
}
