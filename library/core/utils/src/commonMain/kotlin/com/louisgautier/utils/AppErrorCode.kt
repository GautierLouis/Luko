package com.louisgautier.utils

sealed class AppErrorCode : Throwable() {
    data class AppError(override val message: String? = null) : AppErrorCode()

    data class NetworkError(override val message: String? = null) : AppErrorCode()

    data class ServerError(override val message: String? = null) : AppErrorCode()

    data class InvalidCredentials(override val message: String? = null) : AppErrorCode()

    data class UnknownError(override val message: String? = null) : AppErrorCode()
}
