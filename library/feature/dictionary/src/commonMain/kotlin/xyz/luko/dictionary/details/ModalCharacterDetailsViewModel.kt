package xyz.luko.dictionary.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.domain.model.Dictionary
import xyz.luko.domain.model.Session
import xyz.luko.domain.repository.DictionaryRepository
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute

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

    val state: StateFlow<UIState>
        field = MutableStateFlow<UIState>(UIState.Loading)

    init {
        loadCharacter()
    }

    private fun loadCharacter() {
        viewModelScope.launch {
            val dictionary = dictionaryRepository.getByName(characterCode)

            dictionary
                .onSuccess { dictionary ->
                    val sessions = sessionRepository.getLastSessionsFor(characterCode)

                    state.update {
                        UIState.Success(
                            selectedDictionary = dictionary,
                            lastSession = sessions,
                        )
                    }
                }.onFailure {
                    state.update { UIState.Error }
                }
        }
    }

    fun retry() {
        state.update { UIState.Loading }
        loadCharacter()
    }

    fun practice() {
        AppNavigation.navigate(AppRoute.Learning.PracticeCharacter(characterCode), true)
    }
}
