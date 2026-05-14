package xyz.luko.server.logger

import co.touchlab.kermit.Logger

object ServerLogger {
    fun i(
        tag: String,
        cause: Throwable,
        message: String
    ) {
        Logger.i(tag = tag, throwable = cause, messageString = message)
    }
}