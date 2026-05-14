package xyz.luko.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.SimpleDictionary
import xyz.luko.domain.repository.CharacterRepository

class PaginatedResponse(
    private val repository: CharacterRepository,
    private val levels: List<CharacterFrequencyLevel>,
    private val query: String,
) : PagingSource<Int, SimpleDictionary>() {
    override fun getRefreshKey(state: PagingState<Int, SimpleDictionary>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SimpleDictionary> {
        val page = params.key ?: 0
        val response = repository.search(levels, query, page, 100)

        if (response.isFailure) {
            return LoadResult.Error(response.exceptionOrNull()!!)
        }

        val data = response.getOrNull()

        if (data == null) {
            return LoadResult.Error(Throwable("No data from getAll() at page: $page"))
        }

        return LoadResult.Page(
            data = data.data,
            prevKey = if (page == 0) null else page.dec(),
            nextKey = if (data.hasNextPage) page.inc() else null,
        )
    }
}
