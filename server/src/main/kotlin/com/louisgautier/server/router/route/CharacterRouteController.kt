package com.louisgautier.server.router.route

import com.louisgautier.apicontracts.dto.DictionaryWithGraphicDto
import com.louisgautier.apicontracts.routing.EndPoint
import com.louisgautier.server.domain.repo.DictionaryRepository
import com.louisgautier.server.domain.repo.GraphicRepository
import com.louisgautier.server.error.dictionaryNotFound
import com.louisgautier.server.error.graphicNotFound
import com.louisgautier.server.error.missingParameter
import com.louisgautier.server.router.RouteController
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route

class CharacterRouteController(
    private val dictionaryRepository: DictionaryRepository,
    private val graphicRepository: GraphicRepository,
) : RouteController {

    override fun Route.register() {
        get<EndPoint.Level> {
            val result = dictionaryRepository.getLevelCount()
            call.respond(HttpStatusCode.OK, result)
        }

        get<EndPoint.GenerateSession> { resources ->
            val limit = resources.limit
            val levels = resources.levels
                ?: throw missingParameter("levels")

            val result = dictionaryRepository.getRandomCharacters(levels, limit)
            call.respond(HttpStatusCode.OK, result)
        }

        get<EndPoint.Characters.ByLevel> { resources ->
            val page = resources.page
            val limit = resources.limit
            val level = resources.level
                ?: throw missingParameter("level")

            val result = dictionaryRepository.getByLevel(page, limit, level)
            call.respond(HttpStatusCode.OK, result)
        }

        get<EndPoint.Characters.Search> { resources ->
            val page = resources.page
            val limit = resources.limit
            val levels = resources.levels
                ?: throw missingParameter("levels")
            val query = resources.query

            val result = dictionaryRepository.search(
                levels,
                query,
                page,
                limit
            )
            call.respond(HttpStatusCode.OK, result)
        }

        get<EndPoint.Characters.ByName> { resource ->
            val dictionary = dictionaryRepository.get(resource.code)
                ?: throw dictionaryNotFound(resource.code)

            val graphic = graphicRepository.get(resource.code)
                ?: throw graphicNotFound(resource.code)

            call.respond(HttpStatusCode.OK, DictionaryWithGraphicDto(dictionary, graphic))
        }

        get<EndPoint.Characters.ByName.Graphic> { resource ->
            val result = graphicRepository.get(resource.parent.code)
                ?: throw graphicNotFound(resource.parent.code)

            call.respond(HttpStatusCode.OK, result)
        }
    }
}