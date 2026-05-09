package com.louisgautier.server.domain.usecase

import com.louisgautier.apicontracts.dto.DictionaryWithGraphicDto
import com.louisgautier.apicontracts.routing.EndPoint
import com.louisgautier.server.domain.repo.DictionaryRepository
import com.louisgautier.server.domain.repo.GraphicRepository
import com.louisgautier.server.error.dictionaryNotFound
import com.louisgautier.server.error.graphicNotFound

class GetFullDictionaryUseCase(
    private val dictionaryRepository: DictionaryRepository,
    private val graphicRepository: GraphicRepository
) {

    suspend fun get(params: EndPoint.Characters.ByName): DictionaryWithGraphicDto {
        val dictionary = dictionaryRepository.get(params)
            ?: throw dictionaryNotFound(params.code)

        val graphic = graphicRepository.get(params)
            ?: throw graphicNotFound(params.code)

        return DictionaryWithGraphicDto(dictionary, graphic)
    }
}