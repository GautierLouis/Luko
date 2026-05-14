package xyz.luko.server.domain.repo

import xyz.luko.apicontracts.dto.RegisterDeviceRequestDto
import xyz.luko.apicontracts.dto.UserRegistrationResponseJson

interface AuthenticationRepository {
    suspend fun registerAnonymously(device: RegisterDeviceRequestDto): Result<UserRegistrationResponseJson>
    suspend fun refreshSession(refreshToken: String): Result<UserRegistrationResponseJson>
    suspend fun updateFcm(device: RegisterDeviceRequestDto)
}