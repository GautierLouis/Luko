package com.louisgautier.server.domain.model

data class UserEntity(
    val installationId: String,
    val supabaseUserId: String,
    val supabaseRefreshToken: String,
    val fcmToken: String,
    val createdAt: String,
    val updatedAt: String,
) {
    companion object
}