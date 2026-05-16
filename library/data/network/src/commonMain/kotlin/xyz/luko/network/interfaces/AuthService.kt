package xyz.luko.network.interfaces

import xyz.luko.apicontracts.dto.RegisterDeviceRequestDto

interface AuthService {
    suspend fun registerDevice(body: RegisterDeviceRequestDto): Result<Unit>

    suspend fun updateFcm(body: RegisterDeviceRequestDto): Result<Unit>
}
