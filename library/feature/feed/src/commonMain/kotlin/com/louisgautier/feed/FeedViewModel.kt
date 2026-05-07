package com.louisgautier.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow

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
