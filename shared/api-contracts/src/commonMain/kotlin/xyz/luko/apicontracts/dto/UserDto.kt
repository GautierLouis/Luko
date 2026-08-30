package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class UserDto(
    /**
     * FirebaseID, not Exposed ID
     */
    val id: String,
    val fcmToken: String?,
    val platform: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)

@Serializable
data class MeDto(
    val currentStreak: Int,
    val levels: Map<Int, List<Int>>,
)
