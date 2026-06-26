package xyz.luko.sessions.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import xyz.luko.baseui.session.toUiModel
import xyz.luko.sessions.SessionListViewModel
import xyz.luko.ui.core.preview.PreviewProvider
import xyz.luko.ui.designsystem.components.metrics.SessionCard
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.designsystem.token.dimens.Padding
import xyz.luko.ui.designsystem.token.dimens.Spacing

@Composable
internal fun SessionListPane(
    state: SessionListViewModel.UiState,
    lazyColumState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = WindowInsets.systemBars.asPaddingValues(),
    onClick: (Long) -> Unit = {}
) {
    LazyColumn(
        state = lazyColumState,
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.materialColors.background)
            .padding(top = Padding.large)
            .padding(horizontal = Padding.large),
        verticalArrangement = Spacing.large,
        contentPadding = contentPadding
    ) {
        items(
            items = state.sessions,
            key = { it.id },
        ) {
            SessionCard(
                model = it.toUiModel(),
                onClick = { onClick(it.id) }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSessionListPane(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        SessionListPane(
            state = SessionListViewModel.UiState(
                sessions = PreviewProvider.sessionList
            )
        )
    }
}
