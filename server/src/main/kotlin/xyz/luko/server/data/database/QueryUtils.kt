package xyz.luko.server.data.database

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import xyz.luko.server.data.database.table.CharacterTable
import xyz.luko.server.data.database.table.DictionaryTable

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

/** Checks if the column value is not an empty JSON array */
private fun Column<String?>.arrayNotEmpty() = this.neq("[]")

/** Excludes pure radical entries: must have a pinyin or a non-empty decomposition list */
private fun SqlExpressionBuilder.notRadical() =
    CharacterTable.pinyin.isNotNull() or DictionaryTable.decomposition.arrayNotEmpty()

/** Restricts to characters that belong to a valid frequency level */
private fun SqlExpressionBuilder.validLevel() =
    DictionaryTable.level.between(1, 3)

/** Applies the default validity filters (notRadical + validLevel) and appends a custom clause */
fun Query.defaultWhere(clause: SqlExpressionBuilder.() -> Op<Boolean>) =
    this.where { notRadical() and validLevel() }.andWhere(clause)

/**
 * Paginates the query and returns whether a next page exists alongside the raw rows.
 * Fetches [limit]+1 rows — the extra row is used as a next-page probe and is not included in the result.
 */
internal fun Query.paginated(page: Int, limit: Int): Pair<Boolean, List<ResultRow>> {
    val rows = limit(limit + 1)
        .offset(page * limit.toLong())
        .toList()
    return (rows.size > limit) to rows.take(limit)
}

/**
 * Expands a plain pinyin query into all its tone accent variants
 * e.g. :
 * "shi" -> ["shi", "shī", "shí", "shǐ", "shì"]
 * */
fun String.toneVariants(): List<String> {
    val variants = mutableListOf(this)
    val toneMap = mapOf(
        'a' to listOf("ā", "á", "ǎ", "à"),
        'e' to listOf("ē", "é", "ě", "è"),
        'i' to listOf("ī", "í", "ǐ", "ì"),
        'o' to listOf("ō", "ó", "ǒ", "ò"),
        'u' to listOf("ū", "ú", "ǔ", "ù", "ü", "ǖ", "ǘ", "ǚ", "ǜ")
    )
    toneMap.forEach { (plain, accented) ->
        if (contains(plain)) {
            accented.forEach {
                variants.add(replace(plain.toString(), it))
            }
        }
    }
    return variants
}

/** Builds a pinyin LIKE condition matching any tone variant of this query string */
internal fun matchesPinyin(query: String): Op<Boolean> =
    query.toneVariants()
        .map { v -> Op.build { CharacterTable.pinyin like "%$v%" } }
        .reduce { acc, op -> acc or op }

// --- Utils ---
internal suspend fun IntIdTable.exist() = suspendTransaction { selectAll().count() > 0 }

internal suspend fun <T : Table, E> T.insertAll(
    data: Iterable<E>,
    body: BatchInsertStatement.(E) -> Unit
) = suspendTransaction {
    batchInsert(
        data,
        ignore = true,
        shouldReturnGeneratedValues = true,
        body = body
    )
}
