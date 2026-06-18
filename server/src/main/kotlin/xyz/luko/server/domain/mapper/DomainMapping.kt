package xyz.luko.server.domain.mapper

import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow
import xyz.luko.apicontracts.dto.AuthRegistrationDto
import xyz.luko.apicontracts.dto.DecompositionDto
import xyz.luko.apicontracts.dto.FcmUpdateDto
import xyz.luko.apicontracts.dto.ResponseListDto
import xyz.luko.apicontracts.dto.StrokeDto
import xyz.luko.server.domain.model.CharacterRow
import xyz.luko.server.domain.model.DictionaryRow
import xyz.luko.server.domain.model.GraphicRow
import xyz.luko.server.domain.model.HanziRow
import xyz.luko.server.domain.model.HskEntryRow
import xyz.luko.server.domain.model.HskFormRow
import xyz.luko.server.domain.model.HskTranscriptionsRow
import xyz.luko.server.domain.model.PaginatedRow
import xyz.luko.server.domain.model.PrepopulateRow
import xyz.luko.server.domain.model.UpdateUserRow
import xyz.luko.server.domain.model.UserRow
import xyz.luko.server.domain.model.source.CharacterSource
import xyz.luko.server.domain.model.source.GraphicSource
import xyz.luko.server.domain.model.source.HanziSource
import xyz.luko.server.domain.model.source.HskEntrySource
import xyz.luko.server.domain.model.source.HskFormSource
import xyz.luko.server.domain.model.source.HskTranscriptionsSource

internal object DomainMapping {
    @JvmName("toRowCharacter")
    fun List<CharacterSource>.toRow() = map { it.toRow() }

    @JvmName("toRowHskForm")
    fun List<HskFormSource>.toRow() = map { it.toRow() }

    @JvmName("toRowGraphic")
    fun List<GraphicSource>.toRow() = map { it.toRow() }

    @JvmName("toRowHanzi")
    fun List<HanziSource>.toRow() = map { it.toRow() }

    @JvmName("toRowHskEntry")
    fun List<HskEntrySource>.toRow() = map { it.toRow() }

    fun GraphicSource.toRow() = GraphicRow(
        code = character.code(),
        strokes = Json.encodeToString(strokes),
        medians = Json.encodeToString(medians),
    )

    fun HanziSource.toRow() = HanziRow(
        thisTable = this_table,
        code = simplified.code(),
        traditional = traditional,
        pinyin = pinyin,
        pinyinStyle2 = pinyin_style2,
        zhuyinBopomofo = zhuyin_bopomofo,
        jyupting = jyupting,
        decomposition1 = decomposition1,
        decomposition2WithRadical = decomposition2_with_radical,
        meaningDecomposition2WithRadical = meaning_decomposition2_with_radical,
        decomposition3Graphical = decomposition3_graphical,
        componentIn = component_in,
        exampleWords = example_words,
        meaningJunda = meaning_junda,
        keyWordRsh = key_word_rsh,
        hsk30Level = hsk30_level,
        rankRsh = rank_rsh,
        frequencyJunda = frequency_junda,
        indexGscc = index_gscc,
        learningOrderCcm = learning_order_ccm,
        ccCedictDefinitions = cc_cedict_definitions
    )

    fun HskEntrySource.toRow() = HskEntryRow(
        simplified = simplified,
        radical = radical,
        level = level.joinToString(","),
        frequency = frequency,
        pos = pos.takeIf { it.isNotEmpty() }?.joinToString(","),
        forms = forms.toRow()
    )

    fun HskFormSource.toRow() = HskFormRow(
        traditional = traditional,
        transcriptions = transcriptions?.toRow(),
        meanings = meanings.takeIf { it.isNotEmpty() }?.joinToString("\n"),
        classifiers = classifiers.takeIf { it.isNotEmpty() }?.joinToString(",")
    )

    fun HskTranscriptionsSource.toRow() = HskTranscriptionsRow(
        pinyin = pinyin,
        numeric = numeric,
        wadegiles = wadegiles,
        bopomofo = bopomofo,
        romatzyh = romatzyh
    )

    fun CharacterSource.toRow() = CharacterRow(
        code = character.code(),
        definition = definition,
        pinyin = pinyin.joinToString(),
        decomposition = decomposition,
        etymologyType = etymology?.type.toString(),
        etymologyPhonetic = etymology?.phonetic,
        etymologySemantic = etymology?.semantic,
        etymologyHint = etymology?.hint,
        radical = radical,
        matches = Json.encodeToString(matches)
    )

    fun PrepopulateRow.toRow(
        composition: List<DecompositionDto>,
        medians: List<StrokeDto>
    ) = DictionaryRow(
        code = code,
        char = Char(code).toString(),
        decomposition = Json.encodeToString(composition),
        medians = Json.encodeToString(medians),
        frequency = rankToLevel(frequency),
        hskLevel = hskLevel
    )

    fun AuthRegistrationDto.toRow(
        id: String,
        deviceType: String,
    ) = UserRow(
        id = id,
        fcmToken = fcmToken,
        platform = deviceType,
        createdAt = createdAt.epochSeconds,
        updatedAt = createdAt.epochSeconds,
    )

    fun FcmUpdateDto.toRow(
        id: String,
    ) = UpdateUserRow(
        id = id,
        fcmToken = fcmToken,
        updatedAt = updatedAt.epochSeconds
    )

    fun <T> PaginatedRow.map(map: (ResultRow) -> T) = ResponseListDto(hasNextPage, data.map(map))

    private fun rankToLevel(rank: Int?): Int {
        if (rank == null) return CharacterFrequencyLevel.OBSOLETE.value
        return CharacterFrequencyLevel.entries
            .sortedBy { it.maxRank }
            .firstOrNull { rank <= it.maxRank }?.value
            ?: CharacterFrequencyLevel.OBSOLETE.value
    }


    private fun Char.code() = this.toString().codePointAt(0)
    private fun String.code() = this.codePointAt(0)
    private enum class CharacterFrequencyLevel(val value: Int, val maxRank: Int) {
        COMMON(1, 500),
        FREQUENT(2, 1500),
        STANDARD(3, 3500),
        EXTENDED(4, 7000),
        RARE(5, 9000),
        OBSOLETE(6, Int.MAX_VALUE);
    }
}


