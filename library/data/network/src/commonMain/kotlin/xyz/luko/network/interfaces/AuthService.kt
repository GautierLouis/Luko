package xyz.luko.network.interfaces

import xyz.luko.apicontracts.dto.AuthRegistrationDto
import xyz.luko.apicontracts.dto.FcmUpdateDto

interface AuthService {
    suspend fun registerAnonymously(body: AuthRegistrationDto): Result<Unit>

    suspend fun updateFcm(body: FcmUpdateDto): Result<Unit>
}
