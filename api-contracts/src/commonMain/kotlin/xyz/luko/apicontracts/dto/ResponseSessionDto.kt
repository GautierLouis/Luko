package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseSessionDto<T>(
    val seed: Long,
    val data: List<T>,
)
