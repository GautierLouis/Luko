package com.louisgautier.firebase

import com.louisgautier.logger.AppLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class RemoteConfigManager {
    private val _flags = MutableStateFlow(RemoteConfigFlags())
    val flags = _flags.asSharedFlow()
    val synchronizedFlags: RemoteConfigFlags get() = _flags.value

    private val _completed = MutableStateFlow(false)
    val completed = _completed.asSharedFlow()

    fun register(flags: RemoteConfigFlags) {
        _flags.tryEmit(flags)
        AppLogger.d(tag = "Remote config", message = "Remote config completed")
        _completed.tryEmit(true)
    }

    fun <T> observe(selector: (RemoteConfigFlags) -> T): Flow<T> =
        _flags.map(selector).distinctUntilChanged()
}
