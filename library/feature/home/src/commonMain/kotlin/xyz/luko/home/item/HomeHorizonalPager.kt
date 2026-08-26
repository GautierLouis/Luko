package xyz.luko.home.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.luko.ui.designsystem.components.PageIndicator

@Composable
internal fun <T> HomeHorizonalPager(
    items: List<T>,
    modifier: Modifier = Modifier,
    content: @Composable (Int) -> Unit = {},
) {
    val pagerState = rememberPagerState { items.size }

    val paddings by remember(pagerState.pageCount) {
        derivedStateOf {
            val isFirst = pagerState.currentPage == 0
            val isLast = pagerState.currentPage == pagerState.pageCount - 1
            PaddingValues(
                start = if (isFirst) 8.dp else 24.dp,
                end = if (isLast) 8.dp else 24.dp,
            )
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier.fillMaxWidth(),
            contentPadding = paddings,
            pageSpacing = 8.dp,
            pageContent = { content(it) }
        )

        if (pagerState.pageCount > 1) {
            PageIndicator(
                pagerState = pagerState,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
