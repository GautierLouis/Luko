package com.louisgautier.learning.builder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louisgautier.domain.mapper.LevelCount
import com.louisgautier.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SessionBuilderViewModel(
    private val repository: CharacterRepository,
) : ViewModel() {

    private var _levels = MutableStateFlow<List<LevelCount>>(emptyList())
    val levels = _levels.asStateFlow()

    init {
//        getLevelCount()
    }

    private fun getLevelCount() {
        viewModelScope.launch {
            repository.getLevelCount()
                .onSuccess { data ->
                    _levels.update { data }
                }.onFailure {

                }
        }
    }
}