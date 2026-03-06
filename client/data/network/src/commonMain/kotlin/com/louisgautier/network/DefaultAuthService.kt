package com.louisgautier.network

import com.louisgautier.apicontracts.dto.RegisterDeviceRequestDto
import com.louisgautier.apicontracts.routing.EndPoint
import com.louisgautier.network.interfaces.AuthService
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody

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
}
