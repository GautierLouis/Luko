package xyz.luko.server.database

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import xyz.luko.server.database.entity.DictionaryTable
import xyz.luko.server.database.entity.DictionaryTable.decompositionList
import xyz.luko.server.database.entity.DictionaryTable.level
import xyz.luko.server.database.entity.DictionaryTable.pinyin
import xyz.luko.server.database.entity.GraphicTable
import xyz.luko.server.domain.model.CharacterFrequencyLevelEntity
import xyz.luko.server.domain.repo.implem.toneVariants

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

/** Checks if the column value is not an empty JSON array */
private fun Column<String?>.arrayNotEmpty() = this.neq("[]")

/** Excludes pure radical entries: must have a pinyin or a non-empty decomposition list */
private fun SqlExpressionBuilder.notRadical() =
    pinyin.isNotNull() or decompositionList.arrayNotEmpty()

/** Restricts to characters that belong to a valid frequency level */
private fun SqlExpressionBuilder.validLevel() =
    level inList CharacterFrequencyLevelEntity.validEntry

/** Applies the default validity filters (notRadical + validLevel) and appends a custom clause */
fun Query.defaultWhere(clause: SqlExpressionBuilder.() -> Op<Boolean>) =
    this.where { notRadical() and validLevel() }.andWhere(clause)

/** Joins the dictionary table with the graphic table on the character code */
fun DictionaryTable.joinGraphic() =
    this.join(GraphicTable, JoinType.INNER, code, GraphicTable.code)

/**
 * Paginates the query and returns whether a next page exists alongside the raw rows.
 * Fetches [limit]+1 rows — the extra row is used as a next-page probe and is not included in the result.
 */
fun Query.paginated(page: Int, limit: Int): Pair<Boolean, List<ResultRow>> {
    val rows = limit(limit + 1)
        .offset(page * limit.toLong())
        .toList()
    return (rows.size > limit) to rows.take(limit)
}

/** Builds a pinyin LIKE condition matching any tone variant of this query string */
fun SqlExpressionBuilder.matchesPinyin(query: String): Op<Boolean> =
    query.toneVariants()
        .map { v -> Op.build { pinyin like "%$v%" } }
        .reduce { acc, op -> acc or op }