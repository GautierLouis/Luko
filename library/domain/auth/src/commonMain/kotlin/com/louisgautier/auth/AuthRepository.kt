package com.louisgautier.auth

interface AuthRepository {
    suspend fun registerAnonymously(): Result<Unit>

    suspend fun registerNewToken(token: String): Result<Unit>
}
