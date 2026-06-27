package xyz.luko.server.data.database

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.between
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.isNotNull
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.core.neq
import org.jetbrains.exposed.v1.core.or
import org.jetbrains.exposed.v1.core.statements.BatchInsertStatement
import org.jetbrains.exposed.v1.jdbc.Query
import org.jetbrains.exposed.v1.jdbc.andWhere
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import xyz.luko.server.data.database.table.CharacterTable
import xyz.luko.server.data.database.table.DictionaryTable
import xyz.luko.server.domain.model.PaginatedRow

/** Checks if the column value is not an empty JSON array */
private fun Column<String?>.arrayNotEmpty() = this.neq("[]")

/** Excludes pure radical entries: must have a pinyin or a non-empty decomposition list */
private fun notRadical() =
    CharacterTable.pinyin.isNotNull() or DictionaryTable.decomposition.arrayNotEmpty()

/** Restricts to characters that belong to a valid frequency level */
private fun validLevel() =
    DictionaryTable.level.between(1, 3)

/** Applies the default validity filters (notRadical + validLevel) and appends a custom clause */
fun Query.defaultWhere(clause: () -> Op<Boolean>) =
    this.where { notRadical() and validLevel() }.andWhere(clause)

/**
 * Paginates the query and returns whether a next page exists alongside the raw rows.
 * Fetches [limit]+1 rows — the extra row is used as a next-page probe and is not included in the result.
 */
internal fun Query.paginated(page: Int, limit: Int): PaginatedRow {
    val rows = limit(limit + 1)
        .offset(page * limit.toLong())
        .toList()
    return PaginatedRow(rows.take(limit), rows.size > limit)
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
        .map<String, Op<Boolean>> { v -> CharacterTable.pinyin like "%$v%" }
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
