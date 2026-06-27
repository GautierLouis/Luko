package xyz.luko.server.data.database.dao

import kotlinx.serialization.json.Json
import org.jetbrains.exposed.v1.core.JoinType
import org.jetbrains.exposed.v1.core.notLike
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import xyz.luko.server.data.database.StatementMapping.add
import xyz.luko.server.data.database.exist
import xyz.luko.server.data.database.insertAll
import xyz.luko.server.data.database.table.CharacterTable
import xyz.luko.server.data.database.table.GraphicTable
import xyz.luko.server.data.database.table.HanziTable
import xyz.luko.server.data.database.table.HskEntryCharacterTable
import xyz.luko.server.data.database.table.HskEntryLevelTable
import xyz.luko.server.data.database.table.HskEntryTable
import xyz.luko.server.data.database.table.HskFormTable
import xyz.luko.server.domain.model.CharacterRow
import xyz.luko.server.domain.model.GraphicRow
import xyz.luko.server.domain.model.HanziRow
import xyz.luko.server.domain.model.HskEntryRow
import xyz.luko.server.domain.model.PrepopulateRow

interface PrepopulateDao {
    suspend fun isGraphicInitialized(): Boolean
    suspend fun isCharacterInitialized(): Boolean
    suspend fun isHanziInitialized(): Boolean
    suspend fun isVocabularyInitialized(): Boolean

    suspend fun insertGraphicFile(data: List<GraphicRow>)
    suspend fun insertCharacterFile(data: List<CharacterRow>)
    suspend fun insertHanziFile(data: List<HanziRow>)
    suspend fun insertVocabulary(data: List<HskEntryRow>)

    suspend fun getPrepopulateData(): List<PrepopulateRow>
}

// --- Implementation ---

internal class DefaultPrepopulateDao : PrepopulateDao {
    override suspend fun isGraphicInitialized() = GraphicTable.exist()
    override suspend fun isCharacterInitialized() = CharacterTable.exist()
    override suspend fun isHanziInitialized() = HanziTable.exist()
    override suspend fun isVocabularyInitialized() = HskFormTable.exist()

    override suspend fun insertGraphicFile(data: List<GraphicRow>) {
        GraphicTable.insertAll(data) { graphic -> this.add(graphic) }
    }

    override suspend fun insertCharacterFile(data: List<CharacterRow>) {
        CharacterTable.insertAll(data) { dictionary -> this.add(dictionary) }
    }

    override suspend fun insertHanziFile(data: List<HanziRow>) {
        HanziTable.insertAll(data) { hanzi -> this.add(hanzi) }
    }

    override suspend fun insertVocabulary(data: List<HskEntryRow>) {
        val ids = HskEntryTable.insertAll(data) { entry -> this.add(entry) }
            .map { it[HskEntryTable.id].value }

        val forms = data.zip(ids)
            .flatMap { (entry, id) -> entry.forms.map { id to it } }

        val codePoints = data.zip(ids)
            .flatMap { (entry, id) ->
                entry.simplified.map { char ->
                    id to char.code
                }
            }

        val levels = data.zip(ids)
            .flatMap { (entry, id) -> entry.level.split(",").map { l -> id to l } }

        HskFormTable.insertAll(forms) { (entryId, form) -> add(entryId, form) }
        HskEntryCharacterTable.insertAll(codePoints) { (entryId, code) -> add(entryId, code) }
        HskEntryLevelTable.insertAll(levels) { (entryId, level) -> add(entryId, level) }
    }

    override suspend fun getPrepopulateData(): List<PrepopulateRow> {
        return suspendTransaction {
            fun String.hskPriority(): Int = when {
                startsWith("newest-") -> 0
                startsWith("new-") -> 1
                else -> 2
            }

            fun String.hskNumber(): Int? = split("-").lastOrNull()?.toIntOrNull()

            val characterHskLevels: Map<Int, Int> = HskEntryCharacterTable
                .join(
                    HskEntryLevelTable,
                    JoinType.INNER,
                    HskEntryCharacterTable.entryId,
                    HskEntryLevelTable.entryId
                )
                .select(HskEntryCharacterTable.codePoint, HskEntryLevelTable.level)
                .where { HskEntryLevelTable.level notLike "old-%" }
                .toList()
                .groupBy({ it[HskEntryCharacterTable.codePoint] }) { it[HskEntryLevelTable.level] }
                .mapValues { (_, levels) ->
                    levels.minWithOrNull(compareBy({ it.hskPriority() }, { it.hskNumber() }))
                        ?.hskNumber() ?: 0
                }

            CharacterTable
                .join(GraphicTable, JoinType.INNER, CharacterTable.code, GraphicTable.code)
                .join(HanziTable, JoinType.LEFT, CharacterTable.code, HanziTable.code)
                .select(
                    CharacterTable.code,
                    CharacterTable.decomposition,
                    GraphicTable.medians,
                    HanziTable.frequencyJunda
                ).toList().map {
                    PrepopulateRow(
                        code = it[CharacterTable.code],
                        decomposition = it[CharacterTable.decomposition],
                        medians = Json.Default.decodeFromString(it[GraphicTable.medians]),
                        frequency = it[HanziTable.frequencyJunda],
                        hskLevel = characterHskLevels[it[CharacterTable.code]] ?: -1
                    )
                }
        }
    }
}

