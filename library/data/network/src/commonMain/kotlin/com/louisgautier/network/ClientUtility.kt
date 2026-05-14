package com.louisgautier.network

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import kotlinx.io.IOException
import xyz.luko.logger.AppLogger
import xyz.luko.utils.AppErrorCode
import kotlin.coroutines.cancellation.CancellationException

internal suspend inline fun <reified T> call(request: suspend () -> HttpResponse): Result<T> =
    try {
        val response = request()
        Result.success(response.body<T>())
    } catch (e: CancellationException) {
        AppLogger.e(message = e.message)
        Result.failure(e.toAppAppErrorCode())
    } catch (e: ResponseException) {
        AppLogger.e(message = e.message)
        Result.failure(e.toAppAppErrorCode())
    } catch (e: Throwable) {
        AppLogger.e(message = e.message)
        Result.failure(e.toAppAppErrorCode())
    }

internal fun Throwable.toAppAppErrorCode() = when (this) {
    is CancellationException -> AppErrorCode.AppError()
    is ClientRequestException -> {
        when (response.status.value) {
            401, 403 -> AppErrorCode.InvalidCredentials()
            else -> AppErrorCode.ServerError()
        }
    }

    is RedirectResponseException,
    is ServerResponseException /*5xx*/ -> AppErrorCode.ServerError()

    is IOException -> AppErrorCode.NetworkError() // no internet, timeouts, etc.
    else -> AppErrorCode.UnknownError()
}
