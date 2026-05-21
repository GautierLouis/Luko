package xyz.luko.learning.session.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.luko.learning.session.model.SessionScreenEvent
import xyz.luko.learning.session.model.SessionState

@Composable
internal fun Pager(
    pagerState: PagerState,
    state: SessionState.Success,
    modifier: Modifier = Modifier,
    onEvent: (SessionScreenEvent) -> Unit = {},
) {
    HorizontalPager(
        modifier = modifier,
        userScrollEnabled = false,
        state = pagerState,
    ) {
        GraphicSketcher(
            state = state.currentDrawingPageState,
            onEvent = onEvent,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
