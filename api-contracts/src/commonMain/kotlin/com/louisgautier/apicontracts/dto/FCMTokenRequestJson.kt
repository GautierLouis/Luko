package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Serializable
data class UpdateFcmTokenRequest(
    val userId: String,
    val fcmToken: String,
    val platform: Platform,
    val timestamp: Long = Clock.System.now().toEpochMilliseconds()
)

data class UpdateFcmTokenResponse(
    val success: Boolean,
    val message: String? = null
)