package com.louisgautier.dictionary.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louisgautier.domain.model.DictionaryWithGraphic
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.repository.CharacterRepository
import com.louisgautier.domain.repository.SessionRepository
import com.louisgautier.navigation.AppNavigation
import com.louisgautier.navigation.AppRoute.LearningRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ModalCharacterDetailsViewModel(
    private val characterCode: Int,
    private val characterRepository: CharacterRepository,
    private val sessionRepository: SessionRepository,
) : ViewModel() {
    sealed class UIState {
        data class Success(
            val selectedDictionary: DictionaryWithGraphic,
            val lastSession: List<Session> = emptyList(),
        ) : UIState()

        data object Error : UIState()

        data object Loading : UIState()
    }

    private val _state: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val state = _state.asStateFlow()

    init {
        loadCharacter()
    }

    private fun loadCharacter() {
        viewModelScope.launch {
            val dictionary = characterRepository.getByName(characterCode)

            dictionary
                .onSuccess { dictionary ->
                    val sessions = sessionRepository.getLastSessionsFor(characterCode)

                    _state.update { current ->
                        UIState.Success(
                            selectedDictionary = dictionary,
                            lastSession = sessions,
                        )
                    }
                }.onFailure {
                    _state.update { UIState.Error }
                }
        }
    }

    fun retry() {
        _state.update { UIState.Loading }
        loadCharacter()
    }

    fun practice() {
        AppNavigation.navigate(LearningRoute.PracticeCharacterRoute(characterCode), true)
    }
}
