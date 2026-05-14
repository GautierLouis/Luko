package xyz.luko.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import xyz.luko.domain.model.Session
import xyz.luko.domain.repository.SessionRepository

internal class FeedViewModel(
    private val sessionRepository: SessionRepository,
) : ViewModel() {
    data class UIState(
        val sessions: Flow<PagingData<Session>> = emptyFlow(),
    )

    private val _state = MutableStateFlow(UIState())
    val state = _state.asStateFlow()

    val sessions =
        sessionRepository
            .getSessions()
            .cachedIn(viewModelScope)
}
