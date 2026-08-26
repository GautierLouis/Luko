package xyz.luko.dictionary.home

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.SimpleDictionary
import xyz.luko.domain.paging.PaginatedResponse
import xyz.luko.domain.repository.DictionaryRepository

@OptIn(ExperimentalCoroutinesApi::class)
internal class DictionaryListViewModel(
    private val dictionaryRepository: DictionaryRepository,
) : ViewModel() {
    internal data class UIState(
        val selectedCharacter: Int? = null,
        val filterMenuExpended: Boolean = false,
        val textFieldState: TextFieldState = TextFieldState(),
        val activeFilter: ActiveFilter = ActiveFilter(),
    )

    val state: StateFlow<UIState>
        field = MutableStateFlow(UIState())

    private val debouncedQuery =
        snapshotFlow {
            state.value.textFieldState.text
                .toString()
        }.map { it.trim() }
            .distinctUntilChanged()

    val items: Flow<PagingData<SimpleDictionary>> =
        combine(
            flow = state.map { it.activeFilter }.distinctUntilChanged(),
            flow2 = debouncedQuery,
        ) { filter, query -> filter to query }
            .distinctUntilChanged()
            .flatMapLatest { (filter, query) -> createPaging(filter, query) }
            .cachedIn(viewModelScope)

    private fun createPaging(
        filter: ActiveFilter,
        query: String,
    ): Flow<PagingData<SimpleDictionary>> {
        val levels =
            buildList {
                if (filter.isCommonActivated) add(CharacterFrequencyLevel.COMMON)
                if (filter.isFrequentActivated) add(CharacterFrequencyLevel.FREQUENT)
                if (filter.isStandardActivated) add(CharacterFrequencyLevel.STANDARD)
            }

        return Pager(PagingConfig(pageSize = 100)) {
            PaginatedResponse(dictionaryRepository, levels, query)
        }.flow.cachedIn(viewModelScope)
    }

    fun onEventReceived(event: DictionaryScreenEvent) =
        when (event) {
            is DictionaryScreenEvent.OnCharacterClicked -> onCharacterClicked(event.code)
            is DictionaryScreenEvent.OnFilterToggle -> toggleFilterMenu()
            is DictionaryScreenEvent.OnFilterChange -> updateActiveFilter(event.activeFilter)
            is DictionaryScreenEvent.OnSearch -> {}
            is DictionaryScreenEvent.OnModalDismiss -> dismissModal()
        }

    private fun onCharacterClicked(code: Int) {
        state.update { current -> current.copy(selectedCharacter = code) }
    }

    private fun toggleFilterMenu() {
        state.update { current -> current.copy(filterMenuExpended = !current.filterMenuExpended) }
    }

    private fun updateActiveFilter(activeFilter: ActiveFilter) {
        state.update { current -> current.copy(activeFilter = activeFilter) }
    }

    private fun dismissModal() {
        state.update { current -> current.copy(selectedCharacter = null) }
    }
}
