package com.louisgautier.server.domain.repo.implem

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevelDto
import com.louisgautier.apicontracts.dto.DictionaryDto
import com.louisgautier.apicontracts.dto.GraphicDto
import com.louisgautier.apicontracts.dto.PointDto
import com.louisgautier.apicontracts.dto.StrokeDto
import com.louisgautier.server.database.source.FileDataSource
import com.louisgautier.server.domain.repo.CompositionUseCase
import com.louisgautier.server.domain.repo.FileRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

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