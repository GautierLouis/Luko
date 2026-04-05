package com.louisgautier.preferences

import android.content.Context
import okio.Path
import okio.Path.Companion.toPath

class AndroidDataStore(
    private val context: Context
) : DataStore {

    override fun getPath(name: String): Path {
        return context.filesDir.resolve(name).absolutePath.toPath()
    }
}
