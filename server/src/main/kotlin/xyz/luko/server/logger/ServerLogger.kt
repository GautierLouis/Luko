package xyz.luko.server.logger

import co.touchlab.kermit.Logger

object ServerLogger {
    fun i(
        message: String,
        cause: Throwable? = null,
        tag: String? = null,
    ) {
        Logger.i(tag = tag ?: "[Server Logger]", throwable = cause, messageString = message)
    }
}
