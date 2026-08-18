package xyz.luko.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun <T> HomeHorizonalPager(
    items: List<T>,
    modifier: Modifier = Modifier,
    content: @Composable (Int) -> Unit = {},
) {
    val pagerState = remember(items.size) { PagerState { items.size } }

    val paddings by remember(pagerState.pageCount) {
        derivedStateOf {
            when (pagerState.currentPage) {
                0 -> PaddingValues(start = 8.dp, end = 24.dp)
                pagerState.pageCount - 1 -> PaddingValues(start = 24.dp, end = 8.dp)
                else -> PaddingValues(horizontal = 24.dp)
            }
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
