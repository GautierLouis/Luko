package com.louisgautier.preferences

import okio.Path

interface DataStore {
    fun getPath(name: String = "dice.preferences_pb"): Path
}
