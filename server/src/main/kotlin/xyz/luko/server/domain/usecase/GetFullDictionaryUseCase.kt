package xyz.luko.server.domain.usecase

import xyz.luko.apicontracts.dto.DictionaryWithGraphicDto
import xyz.luko.apicontracts.routing.EndPoint
import xyz.luko.server.domain.repo.DictionaryRepository
import xyz.luko.server.domain.repo.GraphicRepository
import xyz.luko.server.error.dictionaryNotFound
import xyz.luko.server.error.graphicNotFound

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