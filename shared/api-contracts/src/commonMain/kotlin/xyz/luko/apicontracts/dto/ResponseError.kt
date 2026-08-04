package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable
import kotlin.time.Clock

@Serializable
data class ResponseError(
    val code: String,
    val message: String,
    val timestamp: Long = Clock.System.now().toEpochMilliseconds(),
)
