package com.louisgautier.auth

interface AuthRepository {
    suspend fun registerDevice(): Result<Unit>
}