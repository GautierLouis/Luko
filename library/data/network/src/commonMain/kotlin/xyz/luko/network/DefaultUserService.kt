package xyz.luko.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import xyz.luko.apicontracts.dto.MeDto
import xyz.luko.apicontracts.dto.ReviewAttemptRequest
import xyz.luko.apicontracts.dto.ReviewResultDto
import xyz.luko.apicontracts.routing.Destination
import xyz.luko.network.interfaces.UserService

internal class DefaultUserService(
    private val client: HttpClient,
) : UserService {
    override suspend fun me(): Result<MeDto> =
        call {
            client.get(Destination.Me())
        }

    override suspend fun reviewSession(review: ReviewAttemptRequest): Result<List<ReviewResultDto>> =
        call {
            client.post(Destination.Me.ReviewSession()) { setBody(review) }
        }
}
