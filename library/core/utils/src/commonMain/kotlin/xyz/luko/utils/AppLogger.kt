package xyz.luko.utils

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.platformLogWriter

object AppLogger {
    fun init() {
        Logger.setLogWriters(ChunkedLogWriter(platformLogWriter()))
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

class ChunkedLogWriter(
    private val delegate: LogWriter,
    private val chunkSize: Int = 4000,
) : LogWriter() {

    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        if (message.length <= chunkSize) {
            delegate.log(severity, message, tag, throwable)
            return
        }

        val chunks = splitPreferringLineBreaks(message, chunkSize)
        chunks.forEachIndexed { index, chunk ->
            delegate.log(
                severity = severity,
                message = chunk,
                tag = tag,
                throwable = if (index == 0) throwable else null,
            )
        }
    }

    private fun splitPreferringLineBreaks(message: String, maxSize: Int): List<String> {
        val result = mutableListOf<String>()
        var remaining = message

        while (remaining.length > maxSize) {
            // Prefer cutting at the last newline within the limit
            val lastNewline = remaining.lastIndexOf('\n', startIndex = maxSize)
            val cutAt = if (lastNewline > 0) lastNewline else maxSize // fallback: hard cut

            result += remaining.substring(0, cutAt)
            remaining = remaining.substring(cutAt).trimStart('\n')
        }
        result += remaining
        return result
    }
}
