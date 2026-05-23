package xyz.luko.dictionary.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.domain.model.Dictionary
import xyz.luko.domain.model.Session
import xyz.luko.domain.repository.DictionaryRepository
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.navigation.AppNavigation
import xyz.luko.navigation.AppRoute.LearningRoute

internal class ModalCharacterDetailsViewModel(
    private val characterCode: Int,
    private val dictionaryRepository: DictionaryRepository,
    private val sessionRepository: SessionRepository,
) : ViewModel() {
    sealed class UIState {
        data class Success(
            val selectedDictionary: Dictionary,
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
            val dictionary = dictionaryRepository.getByName(characterCode)

            dictionary
                .onSuccess { dictionary ->
                    val sessions = sessionRepository.getLastSessionsFor(characterCode)

                    _state.update {
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
