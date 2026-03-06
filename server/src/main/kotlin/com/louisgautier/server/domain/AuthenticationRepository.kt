package com.louisgautier.server.domain

import com.louisgautier.apicontracts.dto.RegisterDeviceRequestDto
import com.louisgautier.apicontracts.dto.UserRegistrationResponseJson
import kotlinx.coroutines.flow.Flow

internal interface AuthenticationRepository {
    suspend fun registerAnonymously(device: RegisterDeviceRequestDto): Result<UserRegistrationResponseJson>
    suspend fun refreshSession(refreshToken: String): Result<UserRegistrationResponseJson>
}