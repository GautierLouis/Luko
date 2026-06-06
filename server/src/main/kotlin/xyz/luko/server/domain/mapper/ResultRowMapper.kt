package xyz.luko.server.domain.mapper

import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow
import xyz.luko.apicontracts.dto.CharacterFrequencyLevelDto
import xyz.luko.apicontracts.dto.DecompositionDto
import xyz.luko.apicontracts.dto.DictionaryDto
import xyz.luko.apicontracts.dto.SimpleDictionaryDto
import xyz.luko.server.data.database.table.CharacterTable
import xyz.luko.server.data.database.table.DictionaryTable
import xyz.luko.server.data.database.table.GraphicTable
import xyz.luko.server.data.database.table.SeedTable
import xyz.luko.server.data.database.table.UserTable
import xyz.luko.server.domain.model.SeedRow
import xyz.luko.server.domain.model.UserRow

internal object ResultRowMapping {

    fun ResultRow.toDictionary() = DictionaryDto(
        code = this[DictionaryTable.code],
        pinyin = this[CharacterTable.pinyin].orEmpty().split(","),
        decomposition = Json.decodeFromString<List<DecompositionDto>>(this[DictionaryTable.decomposition].orEmpty()),
        level = CharacterFrequencyLevelDto.fromValue(this[DictionaryTable.level]),
        strokes = Json.decodeFromString<List<String>>(this[GraphicTable.strokes]),
        medians = Json.decodeFromString(this[DictionaryTable.medians]),
        sounds = Json.decodeFromString(this[DictionaryTable.sound])
    )

    fun ResultRow.toSimpleDictionary() = SimpleDictionaryDto(
        code = this[DictionaryTable.code],
        pinyin = this[CharacterTable.pinyin].orEmpty().split(","),
        level = CharacterFrequencyLevelDto.fromValue(this[DictionaryTable.level]),
    )

    fun ResultRow.toDto() = UserRow(
        id = this[UserTable.firebaseUid],
        fcmToken = this[UserTable.fcmToken],
        platform = this[UserTable.platform],
        createdAt = this[UserTable.createdAt],
        updatedAt = this[UserTable.updatedAt]
    )

    fun ResultRow.toSeedRow() = SeedRow(
        seed = this[SeedTable.seed],
        levels = this[SeedTable.levels],
        limit = this[SeedTable.limit]
    )
}
