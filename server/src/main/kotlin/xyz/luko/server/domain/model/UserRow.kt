package xyz.luko.server.domain.model

data class UserRow(
    val id: String,
    val fcmToken: String?,
    val createdAt: Long,
    val updatedAt: Long,
)
