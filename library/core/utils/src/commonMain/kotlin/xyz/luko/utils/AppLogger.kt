package xyz.luko.utils

import co.touchlab.kermit.Logger
import co.touchlab.kermit.platformLogWriter

object AppLogger {
    fun init() {
        Logger.setLogWriters(platformLogWriter())
    }

    fun w(
        tag: String = Logger.tag,
        message: String?,
        throwable: Throwable? = null,
    ) {
        Logger.w(messageString = message.orEmpty(), throwable = throwable, tag = tag)
    }

    fun e(
        tag: String = Logger.tag,
        message: String?,
        throwable: Throwable? = null,
    ) {
        Logger.e(messageString = message.orEmpty(), throwable = throwable, tag = tag)
    }

    fun i(
        tag: String = Logger.tag,
        message: String?,
        throwable: Throwable? = null,
    ) {
        Logger.i(messageString = message.orEmpty(), throwable = throwable, tag = tag)
    }

    fun d(
        tag: String = Logger.tag,
        message: String?,
        throwable: Throwable? = null,
    ) {
        Logger.d(messageString = message.orEmpty(), throwable = throwable, tag = tag)
    }
}
