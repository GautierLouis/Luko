package com.louisgautier.dictionary.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.louisgautier.designsystem.components.page.ErrorContent
import com.louisgautier.designsystem.components.page.LoadingContent
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.dictionary.PagingDataPreviewParameter
import com.louisgautier.domain.model.SimpleDictionary
import com.louisgautier.domain.previewSimpleDataList
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
internal fun DictionaryPage(
    items: LazyPagingItems<SimpleDictionary>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit = {}
) {
    Box(modifier = modifier.clipToBounds()) {
        when {
            items.loadState.refresh is LoadState.Error && items.itemCount == 0 -> {
                ErrorContent(
                    modifier = Modifier.fillMaxHeight(),
                    action = { items.retry() }
                )
            }
            // Initial loading
            items.loadState.refresh is LoadState.Loading && items.itemCount == 0 -> {
                LoadingContent(modifier = Modifier.fillMaxHeight())
            }

            else -> DictionaryPageContent(items, onItemClick = onItemClick)
        }
    }
}

@Preview
@Composable
private fun PreviewDictionaryPageDay(
    @PreviewParameter(DictionaryPageProvider::class) pagingData: PagingData<SimpleDictionary>
) {
    AppThemeWrapper(ThemeMode.Day) {
        DictionaryPage(flowOf(pagingData).collectAsLazyPagingItems())
    }
}

@Preview
@Composable
private fun PreviewDictionaryPageNight(
    @PreviewParameter(DictionaryPageProvider::class) pagingData: PagingData<SimpleDictionary>
) {
    AppThemeWrapper(ThemeMode.Night) {
        DictionaryPage(flowOf(pagingData).collectAsLazyPagingItems())
    }
}

private class DictionaryPageProvider :
    PagingDataPreviewParameter<SimpleDictionary>(previewSimpleDataList)