package com.louisgautier.feed

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.louisgautier.baseui.PagingDataPreviewParameter
import com.louisgautier.baseui.session.toUiModel
import com.louisgautier.designsystem.components.metrics.SessionCard
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.previewSession
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel

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
    sessions: LazyPagingItems<Session>
) {

    Scaffold { paddingValues ->
        LazyColumn {
            items(
                count = sessions.itemCount,
                key = { index -> sessions[index]?.id ?: index }
            ) {
                val session = sessions[it]?.toUiModel() ?: return@items
                SessionCard(
                    model = session,
                )
            }
        }
    }
}

private class FeedScreenProvider :
    PagingDataPreviewParameter<Session>(listOf(previewSession))

@Preview
@Composable
private fun PreviewFeedScreenDay(
    @PreviewParameter(FeedScreenProvider::class) pagingData: PagingData<Session>
) {
    AppThemeWrapper(ThemeMode.Day) {
        FeedScreen(
            state = FeedViewModel.UIState(),
            sessions = flowOf(pagingData).collectAsLazyPagingItems()
        )
    }
}

@Preview
@Composable
private fun PreviewFeedScreenNight(
    @PreviewParameter(FeedScreenProvider::class) pagingData: PagingData<Session>
) {
    AppThemeWrapper(ThemeMode.Night) {
        FeedScreen(
            state = FeedViewModel.UIState(),
            sessions = flowOf(pagingData).collectAsLazyPagingItems()
        )
    }
}


