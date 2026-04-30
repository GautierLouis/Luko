package com.louisgautier.utils

sealed class AppErrorCode : Throwable() {
    data class AppError : AppErrorCode()

    data class NetworkError : AppErrorCode()

    data class ServerError : AppErrorCode()

    data class InvalidCredentials : AppErrorCode()

    data class UnknownError : AppErrorCode()
}
