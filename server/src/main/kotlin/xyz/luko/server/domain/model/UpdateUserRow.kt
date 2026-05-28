package xyz.luko.server.domain.model

data class UpdateUserRow(
    val id: String,
    val fcmToken: String?,
    val updatedAt: Long,
)
