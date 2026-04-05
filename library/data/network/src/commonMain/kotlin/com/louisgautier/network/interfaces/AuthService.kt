package com.louisgautier.network.interfaces

import com.louisgautier.apicontracts.dto.RegisterDeviceRequestDto

interface AuthService {
    suspend fun registerDevice(body: RegisterDeviceRequestDto): Result<Unit>
    suspend fun updateFcm(body: RegisterDeviceRequestDto): Result<Unit>
}
