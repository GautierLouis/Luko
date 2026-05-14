package xyz.luko.server.domain.repo.implem

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import xyz.luko.apicontracts.dto.CharacterFrequencyLevelDto
import xyz.luko.apicontracts.dto.DictionaryDto
import xyz.luko.apicontracts.dto.GraphicDto
import xyz.luko.apicontracts.dto.PointDto
import xyz.luko.apicontracts.dto.StrokeDto
import xyz.luko.server.database.source.FileDataSource
import xyz.luko.server.domain.repo.CompositionUseCase
import xyz.luko.server.domain.repo.FileRepository

class DefaultFileRepository(
    private val source: FileDataSource,
    private val compositionUseCase: CompositionUseCase
) : FileRepository {

    override suspend fun loadGraphicFile(): List<GraphicDto> {
        return source.loadGraphicFile()
            .map { g ->
                GraphicDto(
                    code = g.character.toString().codePointAt(0),
                    strokes = g.strokes,
                    medians = g.medians.map { s ->
                        StrokeDto(s.map { p ->
                            PointDto(p[0], p[1])
                        })
                    }
                )
            }
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
}