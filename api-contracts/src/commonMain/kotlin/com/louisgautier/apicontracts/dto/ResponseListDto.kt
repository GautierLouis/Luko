package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseListDto<T>(
    val hasNextPage: Boolean,
    val data: List<T>,
)

