package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class UserDto(
    val id: String,
    val fcmToken: String?,
    val platform: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)
