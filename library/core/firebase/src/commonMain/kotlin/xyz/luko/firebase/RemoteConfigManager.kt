package xyz.luko.firebase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface RemoteConfigManager {
    fun register(flags: RemoteConfigFlags)
    fun <T> observe(selector: (RemoteConfigFlags) -> T): Flow<T>
    val completed: SharedFlow<Boolean>
    val flags: SharedFlow<RemoteConfigFlags>
    val synchronizedFlags: RemoteConfigFlags
}