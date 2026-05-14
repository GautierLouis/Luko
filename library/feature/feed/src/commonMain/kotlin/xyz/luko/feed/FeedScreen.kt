package xyz.luko.feed

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.flowOf
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.baseui.PagingDataPreviewParameter
import xyz.luko.baseui.session.toUiModel
import xyz.luko.designsystem.components.metrics.SessionCard
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.domain.model.Session
import xyz.luko.domain.previewSession

@Composable
fun FeedScreen() {
    val viewModel = koinViewModel<FeedViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sessions = viewModel.sessions.collectAsLazyPagingItems()
    FeedScreen(state, sessions)
}

@Composable
private fun FeedScreen(
    state: FeedViewModel.UIState,
    sessions: LazyPagingItems<Session>,
) {
    Scaffold { paddingValues ->
        LazyColumn {
            items(
                count = sessions.itemCount,
                key = { index -> sessions[index]?.id ?: index },
            ) {
                val session = sessions[it]?.toUiModel() ?: return@items
                SessionCard(
                    model = session,
                )
            }
        }
    }
}

private class FeedScreenProvider : PagingDataPreviewParameter<Session>(listOf(previewSession))

@Preview
@Composable
private fun PreviewFeedScreenDay(
    @PreviewParameter(FeedScreenProvider::class) pagingData: PagingData<Session>,
) {
    AppTheme(ThemeMode.Day) {
        FeedScreen(
            state = FeedViewModel.UIState(),
            sessions = flowOf(pagingData).collectAsLazyPagingItems(),
        )
    }
}

@Preview
@Composable
private fun PreviewFeedScreenNight(
    @PreviewParameter(FeedScreenProvider::class) pagingData: PagingData<Session>,
) {
    AppTheme(ThemeMode.Night) {
        FeedScreen(
            state = FeedViewModel.UIState(),
            sessions = flowOf(pagingData).collectAsLazyPagingItems(),
        )
    }
}
