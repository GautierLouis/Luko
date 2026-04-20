package com.louisgautier.server.router.route

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevelDto
import com.louisgautier.apicontracts.dto.DictionaryWithGraphicDto
import com.louisgautier.apicontracts.dto.ResponseError
import com.louisgautier.apicontracts.routing.EndPoint
import com.louisgautier.server.domain.repo.DictionaryRepository
import com.louisgautier.server.domain.repo.GraphicRepository
import com.louisgautier.server.router.RouteController
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

class CharacterRouteController(
    private val dictionaryRepository: DictionaryRepository,
    private val graphicRepository: GraphicRepository,
) : RouteController {

    override fun Route.register() {
        get<EndPoint.Level> {
            val result = dictionaryRepository.getLevelCount()
            call.respond(HttpStatusCode.OK, result)
        }

        get<EndPoint.GenerateSession> {
            val levels = call.request.queryParameters["level"]?.split(",")
                ?.map { CharacterFrequencyLevelDto.valueOf(it) }
                ?: CharacterFrequencyLevelDto.entries
            val limit = call.request.queryParameters["limit"]?.toInt() ?: 100

            val result = dictionaryRepository.getRandomCharacters(levels, limit)

            call.respond(HttpStatusCode.OK, result)
        }

        get<EndPoint.Characters.ByLevel> {
            val page = call.request.queryParameters["page"]?.toInt() ?: 0
            val limit = call.request.queryParameters["limit"]?.toInt() ?: 100
            val level = call.request.queryParameters["level"]
                ?.let { CharacterFrequencyLevelDto.valueOf(it) }

            if (level == null) {
                call.respond(HttpStatusCode.BadRequest, ResponseError("Missing level parameter"))
                return@get
            }

            dictionaryRepository.getByLevel(page, limit, level).let {
                call.respond(HttpStatusCode.OK, it)
            }
        }

        get<EndPoint.Characters.ByName> { resource ->

            val dictionary = dictionaryRepository.get(resource.code)
            val graphic = graphicRepository.get(resource.code)

            if (dictionary == null || graphic == null) {
                call.respond(
                    HttpStatusCode.NotFound,
                    ResponseError("Glyph not found: ${resource.code}")
                )
            }

            call.respond(HttpStatusCode.OK, DictionaryWithGraphicDto(dictionary!!, graphic!!))
        }

        get<EndPoint.Characters.ByName.SVG> { resource ->

            val result = graphicRepository.get(resource.parent.code)

            if (result == null) {
                call.respond(
                    HttpStatusCode.NotFound,
                    ResponseError("SVG not found: ${resource.parent.code}")
                )
            }

            call.respond(HttpStatusCode.OK, result!!)
        }

        get("/") {
            call.respondText("Ktor")
        }
    }
}