package com.louisgautier.dictionary.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louisgautier.domain.model.DictionaryWithGraphic
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.repository.CharacterRepository
import com.louisgautier.domain.repository.SessionRepository
import com.louisgautier.navigation.AppNavigation
import com.louisgautier.navigation.PracticeCharacterKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ModalCharacterDetailsViewModel(
    private val characterCode: Int,
    private val characterRepository: CharacterRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    internal sealed class UiState {
        data class Success(
            val selectedDictionary: DictionaryWithGraphic,
            val lastSession: List<Session> = emptyList(),
        ): UiState()
        data object Error: UiState()
        data object Loading: UiState()
    }

    private val _state: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val state = _state.asStateFlow()

    init {
        loadCharacter()
    }

    private fun loadCharacter() {
        viewModelScope.launch {
            val dictionary = characterRepository.getByName(characterCode)

            dictionary.onSuccess { dictionary ->
                val sessions = sessionRepository.getLastSessionsFor(characterCode)

                _state.update { current ->
                    UiState.Success(
                        selectedDictionary = dictionary,
                        lastSession = sessions
                    )
                }
            }.onFailure {
                _state.update { UiState.Error }
            }
        }
    }

    fun retry() {
        _state.update { UiState.Loading }
        loadCharacter()
    }

    fun practice() {
        AppNavigation.navigate(PracticeCharacterKey(characterCode), true)
    }
}