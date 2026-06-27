package xyz.luko.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import xyz.luko.apicontracts.dto.UserDto
import xyz.luko.apicontracts.routing.Destination
import xyz.luko.network.interfaces.UserService

internal class DefaultUserService(
    private val client: HttpClient,
) : UserService {
    override suspend fun me(): Result<UserDto> =
        call {
            client.get(Destination.Me())
        }
}
