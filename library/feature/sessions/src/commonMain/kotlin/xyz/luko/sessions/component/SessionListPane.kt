package xyz.luko.sessions.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import xyz.luko.baseui.preview.PreviewProvider
import xyz.luko.baseui.session.toUiModel
import xyz.luko.designsystem.components.metrics.SessionCard
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.Spacing
import xyz.luko.sessions.SessionListViewModel

@Composable
internal fun SessionListPane(
    state: SessionListViewModel.UiState,
    lazyColumState: LazyListState = rememberLazyListState(),
    onClick: (Long) -> Unit = {}
) {
    LazyColumn(
        state = lazyColumState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Padding.large)
            .padding(horizontal = Padding.large),
        verticalArrangement = Spacing.large,
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
