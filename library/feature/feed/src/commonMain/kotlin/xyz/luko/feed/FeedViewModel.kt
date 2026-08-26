package xyz.luko.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import xyz.luko.domain.model.Session
import xyz.luko.domain.repository.SessionRepository

internal class FeedViewModel(
    sessionRepository: SessionRepository,
) : ViewModel() {
    data class UIState(
        val sessions: Flow<PagingData<Session>> = emptyFlow(),
    )

    val state: StateFlow<UIState>
        field = MutableStateFlow(UIState())

    val sessions =
        sessionRepository
            .getSessions()
            .cachedIn(viewModelScope)
}
