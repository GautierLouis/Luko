package xyz.luko.server.router

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond


internal suspend inline fun <reified T : Any> ApplicationCall.respondOk(body: T) =
    this.respond(status = HttpStatusCode.OK, message = body)
