package com.louisgautier.baseui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData

abstract class PagingDataPreviewParameter<T : Any>(
    private val data: List<T>,
) : PreviewParameterProvider<PagingData<T>> {
    private val initialData = data.take(data.size / 2)

    private fun loadStates(
        refresh: LoadState = LoadState.NotLoading(endOfPaginationReached = false),
        append: LoadState = LoadState.NotLoading(endOfPaginationReached = false),
    ) = LoadStates(
        refresh = refresh,
        prepend = LoadState.NotLoading(endOfPaginationReached = false),
        append = append,
    )

    override val values: Sequence<PagingData<T>>
        get() =
            sequenceOf(
                // Initial — Error
                PagingData.from(
                    data = emptyList(),
                    sourceLoadStates = loadStates(refresh = LoadState.Error(Exception())),
                ),
                // Initial — Loading
                PagingData.from(
                    data = emptyList(),
                    sourceLoadStates = loadStates(refresh = LoadState.Loading),
                ),
                // Initial — Success
                PagingData.from(
                    data = initialData,
                    sourceLoadStates = loadStates(),
                ),
                // Append — Error
                PagingData.from(
                    data = initialData,
                    sourceLoadStates = loadStates(append = LoadState.Error(Exception())),
                ),
                // Append — Loading
                PagingData.from(
                    data = initialData,
                    sourceLoadStates = loadStates(append = LoadState.Loading),
                ),
                // Append — Success
                PagingData.from(
                    data = data,
                    sourceLoadStates = loadStates(
                        append = LoadState.NotLoading(
                            endOfPaginationReached = true
                        )
                    ),
                ),
            )
}
