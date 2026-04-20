package com.louisgautier.server.plugin

import com.louisgautier.apicontracts.dto.ResponseError
import com.louisgautier.server.error.ErrorCode
import com.louisgautier.server.error.NotResultException
import com.louisgautier.server.error.ParameterExtractionException
import com.louisgautier.server.logger.ServerLogger
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import kotlin.coroutines.cancellation.CancellationException

class ErrorPlugin : Plugin {
    private fun logException(cause: Throwable, message: String? = null) {
        ServerLogger.i(
            "Status Page",
            cause,
            message = message ?: cause.message ?: ErrorCode.DEFAULT_INTERNAL_MESSAGE
        )
    }

    override fun Application.register() {
        install(StatusPages) {
            exception<ParameterExtractionException> { call, cause ->
                logException(cause)
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ResponseError(cause.code, cause.message.orEmpty())
                )
            }

            exception<NotResultException> { call, cause ->
                logException(cause)
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ResponseError(
                        cause.code,
                        cause.message.orEmpty()
                    )
                )
            }

            exception<BadRequestException> { call, cause ->
                logException(cause)
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ResponseError(
                        ErrorCode.BAD_REQUEST,
                        cause.message ?: ErrorCode.DEFAULT_INVALID_PARAMETER
                    )
                )
            }

            exception<CancellationException> { _, e ->
                logException(e)
                throw e
            }

            exception<Throwable> { call, cause ->
                logException(cause)
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = ResponseError(
                        ErrorCode.INTERNAL_ERROR,
                        ErrorCode.DEFAULT_INTERNAL_MESSAGE
                    )
                )
            }
        }
    }
}