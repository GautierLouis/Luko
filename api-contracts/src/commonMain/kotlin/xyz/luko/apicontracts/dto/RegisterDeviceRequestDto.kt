package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterDeviceRequestDto(
    val installationId: String,
    val fcmToken: String,
)
