package com.louisgautier.auth

interface AuthRepository {
    suspend fun registerAnonymously(): Result<Unit>
}