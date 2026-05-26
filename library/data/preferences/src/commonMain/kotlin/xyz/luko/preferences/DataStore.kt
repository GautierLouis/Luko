package xyz.luko.preferences

import okio.Path

interface DataStore {
    fun getPath(name: String = "luko.preferences_pb"): Path
}
