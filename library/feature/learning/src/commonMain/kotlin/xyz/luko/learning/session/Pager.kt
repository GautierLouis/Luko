package xyz.luko.learning.session

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun Pager(
    pagerState: PagerState,
    state: SessionViewModel.SessionState.Success,
    modifier: Modifier = Modifier,
    onEvent: (SessionScreenEvent) -> Unit
) {
    HorizontalPager(
        modifier = modifier,
        userScrollEnabled = false,
        state = pagerState
    ) {
        GraphicSketcher(
            state = state.currentQuestion,
            drawReference = state.drawReference,
            drawHint = state.drawHint,
            onEvent = onEvent,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}