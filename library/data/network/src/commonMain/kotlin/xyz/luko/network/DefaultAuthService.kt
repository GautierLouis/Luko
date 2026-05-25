package xyz.luko.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import xyz.luko.apicontracts.dto.AuthRegistrationDto
import xyz.luko.apicontracts.dto.FcmUpdateDto
import xyz.luko.apicontracts.routing.Destination
import xyz.luko.network.interfaces.AuthService

internal class DefaultAuthService(
    private val client: HttpClient,
) : AuthService {

    override suspend fun registerAnonymously(body: AuthRegistrationDto): Result<Unit> {
        return call {
            client.post(Destination.RegisterAnonymously()) {
                setBody(body)
            }
        }
    }

    override suspend fun updateFcm(body: FcmUpdateDto): Result<Unit> =
        call {
            client.post(Destination.UpdateFcm()) {
                setBody(body)
            }
        }
}
