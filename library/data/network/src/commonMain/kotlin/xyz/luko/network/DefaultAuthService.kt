package xyz.luko.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import xyz.luko.apicontracts.dto.RegisterDeviceRequestDto
import xyz.luko.apicontracts.routing.EndPoint
import xyz.luko.network.interfaces.AuthService

internal class DefaultAuthService(
    private val client: HttpClient,
) : AuthService {

    override suspend fun registerDevice(body: RegisterDeviceRequestDto): Result<Unit> {
        return call {
            client.post(EndPoint.RegisterAnonymously()) {
                setBody(body)
            }
        }
    }

    override suspend fun updateFcm(body: RegisterDeviceRequestDto): Result<Unit> {
        return call {
            client.post(EndPoint.UpdateFcm()) {
                setBody(body)
            }
        }
    }
}
