package com.louisgautier.server.router.route

import com.louisgautier.apicontracts.routing.EndPoint
import com.louisgautier.server.domain.repo.DictionaryRepository
import com.louisgautier.server.domain.repo.GraphicRepository
import com.louisgautier.server.domain.usecase.GetFullDictionary
import com.louisgautier.server.error.graphicNotFound
import com.louisgautier.server.router.RouteController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route

class CharacterRouteController(
    private val dictionaryRepository: DictionaryRepository,
    private val graphicRepository: GraphicRepository,
    private val getFullDictionary: GetFullDictionary
) : RouteController {

    private suspend inline fun <reified T : Any> ApplicationCall.respondOk(body: T) =
        this.respond(status = HttpStatusCode.OK, message = body)

    override fun Route.register() {
        get<EndPoint.Level> {
            dictionaryRepository.getLevelCount().let {
                call.respondOk(it)
            }
        }

        get<EndPoint.GenerateSession> { resources ->
            dictionaryRepository.getRandomCharacters(resources).let {
                call.respondOk(it)
            }
        }

        get<EndPoint.Characters.ByLevel> { resources ->
            dictionaryRepository.getByLevel(resources).let {
                call.respondOk(it)
            }
        }

        get<EndPoint.Characters.Search> { resources ->
            dictionaryRepository.search(resources).let {
                call.respondOk(it)
            }
        }

        get<EndPoint.Characters.ByName> { resource ->
            getFullDictionary.get(resource).let {
                call.respondOk(it)
            }
        }

        get<EndPoint.Characters.ByName.Graphic> { resource ->
            graphicRepository.get(resource.parent)
                ?.let { call.respondOk(it) }
                ?: throw graphicNotFound(resource.parent.code)
        }
    }
}