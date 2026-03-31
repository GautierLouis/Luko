package com.louisgautier.dictionary

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

internal abstract class PagingDataPreviewParameter<T : Any>(
    private val data: List<T>
) : PreviewParameterProvider<PagingData<T>> {

    private fun createLoadStates(state: LoadState) = LoadStates(
        refresh = state,
        prepend = state,
        append = state,
    )

    override val values: Sequence<PagingData<T>>
        get() = sequenceOf(
            PagingData.from(
                data = emptyList(),
                sourceLoadStates = createLoadStates(LoadState.Error(Exception()))
            ),
            PagingData.from(
                data = emptyList(),
                sourceLoadStates = createLoadStates(LoadState.Loading)
            ),
            PagingData.from(
                data = data,
                sourceLoadStates = createLoadStates(
                    state = LoadState.NotLoading(endOfPaginationReached = false)
                )
            ),
        )
}