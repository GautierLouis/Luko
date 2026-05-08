package com.louisgautier.logger

import co.touchlab.kermit.Logger

object AppLogger {
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
