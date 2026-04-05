package com.louisgautier.preferences

import okio.Path
import okio.Path.Companion.toPath
import java.io.File

class JvmDataStore : DataStore {

    override fun getPath(name: String): Path {
        return File(System.getProperty("java.io.tmpdir"), name).absolutePath.toPath()
    }
}