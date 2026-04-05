package com.louisgautier.firebase.notification

interface FcmAccessor {
    suspend fun registerNewToken(token: String): Result<Unit>
}