package com.louisgautier.dictionary.home

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.louisgautier.domain.model.CharacterFrequencyLevel
import com.louisgautier.domain.model.SimpleDictionary
import com.louisgautier.domain.paging.PaginatedResponse
import com.louisgautier.domain.repository.CharacterRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalCoroutinesApi::class)
internal class DictionaryListViewModel(
    private val characterRepository: CharacterRepository,
) : ViewModel() {
    @OptIn(ExperimentalMaterial3Api::class)
    internal data class UIState(
        val selectedCharacter: Int? = null,
        val filterMenuExpended: Boolean = false,
        val textFieldState: TextFieldState = TextFieldState(),
        val activeFilter: ActiveFilter = ActiveFilter(),
    )

    private val _state = MutableStateFlow(UIState())
    private val _activeFilter = MutableStateFlow(ActiveFilter())
    private val debouncedQuery =
        snapshotFlow {
            _state.value.textFieldState.text
                .toString()
        }.map { it.trim() }
            .distinctUntilChanged()

    val state = _state.asStateFlow()
    val items: Flow<PagingData<SimpleDictionary>> =
        combine(
            flow = _activeFilter,
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
            PaginatedResponse(characterRepository, levels, query)
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
        _state.update { current -> current.copy(selectedCharacter = code) }
    }

    private fun toggleFilterMenu() {
        _state.update { current -> current.copy(filterMenuExpended = !current.filterMenuExpended) }
    }

    private fun updateActiveFilter(activeFilter: ActiveFilter) {
        _activeFilter.value = activeFilter
        _state.update { current -> current.copy(activeFilter = activeFilter) }
    }

    private fun dismissModal() {
        _state.update { current -> current.copy(selectedCharacter = null) }
    }
}
