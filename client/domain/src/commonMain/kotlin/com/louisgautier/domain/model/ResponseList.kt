package com.louisgautier.domain.model

data class ResponseList<T>(
    val hasNextPage: Boolean,
    val data: List<T>,
)