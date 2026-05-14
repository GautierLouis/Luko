package xyz.luko.firebase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import xyz.luko.logger.AppLogger

class DefaultRemoteConfigManager : RemoteConfigManager {
    private val _flags = MutableStateFlow(RemoteConfigFlags())
    override val flags = _flags.asSharedFlow()
    override val synchronizedFlags: RemoteConfigFlags get() = _flags.value

    private val _completed = MutableStateFlow(false)
    override val completed = _completed.asSharedFlow()

    override fun register(flags: RemoteConfigFlags) {
        _flags.tryEmit(flags)
        AppLogger.d(tag = "Remote config", message = "Remote config completed")
        _completed.tryEmit(true)
    }

    override fun <T> observe(selector: (RemoteConfigFlags) -> T): Flow<T> =
        _flags.map(selector).distinctUntilChanged()
}