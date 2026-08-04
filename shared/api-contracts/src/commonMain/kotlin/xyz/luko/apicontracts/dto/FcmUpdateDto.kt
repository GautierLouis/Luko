package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.Instant

@Serializable
data class FcmUpdateDto(
    val fcmToken: String?,
    val updatedAt: Instant = Clock.System.now()
)
