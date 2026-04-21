package com.louisgautier.server.database

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevelDto
import com.louisgautier.apicontracts.dto.ResponseListDto
import com.louisgautier.server.database.entity.DictionaryTable
import com.louisgautier.server.database.entity.DictionaryTable.decompositionList
import com.louisgautier.server.database.entity.DictionaryTable.level
import com.louisgautier.server.database.entity.DictionaryTable.pinyin
import com.louisgautier.server.database.entity.GraphicTable
import com.louisgautier.server.domain.repo.implem.toneVariants
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.or

/** Checks if the column value is not an empty JSON array */
private fun Column<String?>.arrayNotEmpty() = this.neq("[]")

/** Excludes pure radical entries: must have a pinyin or a non-empty decomposition list */
private fun SqlExpressionBuilder.notRadical() =
    pinyin.isNotNull() or decompositionList.arrayNotEmpty()

/** Restricts to characters that belong to a valid frequency level */
private fun SqlExpressionBuilder.validLevel() =
    level inList CharacterFrequencyLevelDto.validEntry

/** Applies the default validity filters (notRadical + validLevel) and appends a custom clause */
fun Query.defaultWhere(clause: SqlExpressionBuilder.() -> Op<Boolean>) =
    this.where { notRadical() and validLevel() }.andWhere(clause)

/** Joins the dictionary table with the graphic table on the character code */
fun DictionaryTable.joinGraphic() =
    this.join(GraphicTable, JoinType.INNER, code, GraphicTable.code)

/**
 * Executes the query with pagination and maps each row to [T].
 * Fetches one extra row to determine if a next page exists,
 * then returns a [ResponseListDto] with the [ResponseListDto.hasNextPage] flag and the mapped [ResponseListDto.data].
 */
fun <T> Query.paginated(
    page: Int,
    limit: Int,
    transform: (ResultRow) -> T,
): ResponseListDto<T> {
    val rows = limit(limit + 1)
        .offset(page * limit.toLong())
        .toList()

    val data = rows.dropLast(1).map(transform)
    return ResponseListDto(hasNextPage = rows.size > limit, data = data)
}

/** Builds a pinyin LIKE condition matching any tone variant of this query string */
fun SqlExpressionBuilder.matchesPinyin(query: String): Op<Boolean> =
    query.toneVariants()
        .map { v -> Op.build { pinyin like "%$v%" } }
        .reduce { acc, op -> acc or op }