package com.louisgautier.server.domain.repo

import com.louisgautier.apicontracts.dto.RegisterDeviceRequestDto
import com.louisgautier.apicontracts.dto.UserRegistrationResponseJson

interface AuthenticationRepository {
    suspend fun registerAnonymously(device: RegisterDeviceRequestDto): Result<UserRegistrationResponseJson>
    suspend fun refreshSession(refreshToken: String): Result<UserRegistrationResponseJson>
    suspend fun updateFcm(device: RegisterDeviceRequestDto)
}