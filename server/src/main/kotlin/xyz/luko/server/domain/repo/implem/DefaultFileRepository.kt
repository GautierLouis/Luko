package xyz.luko.server.domain.repo.implem

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import xyz.luko.apicontracts.dto.CharacterFrequencyLevelDto
import xyz.luko.apicontracts.dto.DictionaryDto
import xyz.luko.apicontracts.dto.GraphicDto
import xyz.luko.server.database.source.FileDataSource
import xyz.luko.server.domain.mapper.toDomain
import xyz.luko.server.domain.repo.FileRepository
import xyz.luko.server.domain.usecase.parser.CompositionUseCase
import xyz.luko.server.domain.usecase.parser.SmotherMediansUseCase
import java.awt.Font

class DefaultFileRepository(
    private val source: FileDataSource,
    private val compositionUseCase: CompositionUseCase,
    private val smootherMediansUseCase: SmotherMediansUseCase
) : FileRepository {

    override suspend fun loadGraphicFile(): List<GraphicDto> {
        return source.loadGraphicFile()
            .map { it.toDomain(smootherMediansUseCase.smoothen(it.medians)) }
    }

    override suspend fun loadDictionaryFile(): List<DictionaryDto> {
        val (rawDictionary, hanziRanks) = coroutineScope {
            val dictionary = async { source.loadDictionaryFile() }
            val hanzi = async { parseHanzi() }
            dictionary.await() to hanzi.await()
        }

        return rawDictionary.map {
            val level = hanziRanks.find { h -> h.first == it.character }?.second
            DictionaryDto(
                code = it.character.toString().codePointAt(0),
                definition = it.definition,
                pinyin = it.pinyin,
                decomposition = it.decomposition,
                decompositionList = compositionUseCase.decompose(it.decomposition),
                level = rankToLevel(level),
                etymology = it.etymology,
                radical = it.radical,
                matches = it.matches,
            )
        }
    }

    private fun rankToLevel(rank: Int?): CharacterFrequencyLevelDto = when {
        rank == null -> CharacterFrequencyLevelDto.UNKNOWN
        rank <= 500 -> CharacterFrequencyLevelDto.COMMON
        rank <= 1500 -> CharacterFrequencyLevelDto.FREQUENT
        rank <= 3500 -> CharacterFrequencyLevelDto.STANDARD
        rank <= 7000 -> CharacterFrequencyLevelDto.EXTENDED
        rank <= 9000 -> CharacterFrequencyLevelDto.RARE
        else -> CharacterFrequencyLevelDto.OBSOLETE
    }

    private suspend fun parseHanzi(): List<Pair<Char, Int?>> {
        return source.loadHanziFile().filter { it.simplified != null }
            .map { it.simplified!!.first() to it.rank_rsh }
    }

    override suspend fun loadFont(): Font {
        return withContext(Dispatchers.IO) {
            Font.createFont(Font.TRUETYPE_FONT, source.loadFont().inputStream())
        }
    }
}
