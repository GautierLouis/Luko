package xyz.luko.server.domain.model

import org.jetbrains.exposed.v1.core.ResultRow

data class PaginatedRow(
    val data: List<ResultRow>,
    val hasNextPage: Boolean,
)
