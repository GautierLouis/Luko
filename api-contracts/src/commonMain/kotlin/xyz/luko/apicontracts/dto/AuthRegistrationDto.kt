package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.Instant

@Serializable
data class AuthRegistrationDto(
    val fcmToken: String?,
    val createdAt: Instant = Clock.System.now()
)
