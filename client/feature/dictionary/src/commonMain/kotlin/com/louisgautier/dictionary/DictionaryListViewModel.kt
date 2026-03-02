package com.louisgautier.dictionary

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.louisgautier.domain.model.CharacterFrequencyLevel
import com.louisgautier.domain.model.DictionaryWithGraphic
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.model.SimpleDictionary
import com.louisgautier.domain.repository.CharacterRepository
import com.louisgautier.domain.repository.SessionRepository
import com.louisgautier.domain.utils.PaginatedResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@OptIn(ExperimentalCoroutinesApi::class)
class DictionaryListViewModel(
    private val characterRepository: CharacterRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    @OptIn(ExperimentalMaterial3Api::class)
    data class UIState(
        val selectedTab: DictionaryTab = DictionaryTab.COMMON,
        val dictionaries: Map<DictionaryTab, Flow<PagingData<SimpleDictionary>>> = emptyMap(),
        val selectedDictionary: DictionaryWithGraphic? = null,
        val lastSessions: List<Session> = emptyList(), // lastSession linked to selectedDictionary
    ) {
        val selectedTabIndex
            get() = selectedTab.ordinal
    }

    private var _state = MutableStateFlow(
        UIState(
        dictionaries = DictionaryTab.entries.associateWith { tab -> createPaging(tab) }
    ))
    val state = _state.asStateFlow()

    private fun createPaging(tab: DictionaryTab): Flow<PagingData<SimpleDictionary>> {
        val level = when (tab) {
            DictionaryTab.COMMON -> CharacterFrequencyLevel.COMMON
            DictionaryTab.FREQUENT -> CharacterFrequencyLevel.FREQUENT
            DictionaryTab.STANDARD -> CharacterFrequencyLevel.STANDARD
        }

        val flow = Pager(PagingConfig(pageSize = 50)) {
            PaginatedResponse(characterRepository, level)
        }.flow.cachedIn(viewModelScope)

        return flow
    }

    fun setSelectedTab(tab: DictionaryTab) {
        _state.update { it.copy(selectedTab = tab) }
    }

    fun setSelectedDictionary(code: Int?) {
        if (code == null) {
            _state.update { current -> current.copy(selectedDictionary = null) }
            return
        }

        viewModelScope.launch {
            val dictionary = characterRepository.getByName(code)

            dictionary.onSuccess { dictionary ->
                val sessions = sessionRepository.getLastSessions(code)

                _state.update { current ->
                    current.copy(
                        selectedDictionary = dictionary,
                        lastSessions = sessions
                    )
                }
            }
        }
    }
}