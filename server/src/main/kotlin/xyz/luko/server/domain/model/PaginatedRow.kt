package xyz.luko.server.domain.model

import org.jetbrains.exposed.sql.ResultRow

data class PaginatedRow(
    val data: List<ResultRow>,
    val hasNextPage: Boolean,
)
