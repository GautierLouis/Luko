package com.louisgautier.server.mock

import com.louisgautier.apicontracts.dto.RegisterDeviceRequestDto
import com.louisgautier.apicontracts.dto.UserAuthMethodProvider
import com.louisgautier.apicontracts.dto.UserInfoJson
import com.louisgautier.apicontracts.dto.UserMetadata
import com.louisgautier.apicontracts.dto.UserRegistrationResponseJson
import com.louisgautier.apicontracts.dto.UserTokenJson
import com.louisgautier.server.domain.repo.AuthenticationRepository

class FakeAuthRepo : AuthenticationRepository {
    private val user = UserRegistrationResponseJson(
        user = UserInfoJson(id = "1", provider = listOf(UserAuthMethodProvider.ANONYMOUS)),
        session = UserTokenJson(accessToken = "", refreshToken = "", expiresIn = 0L),
        metadata = UserMetadata(installationID = "", fcmToken = ""),
    )

    override suspend fun registerAnonymously(device: RegisterDeviceRequestDto): Result<UserRegistrationResponseJson> {
        return Result.success(user)
    }

    override suspend fun refreshSession(refreshToken: String): Result<UserRegistrationResponseJson> {
        return Result.success(user)
    }

    override suspend fun updateFcm(device: RegisterDeviceRequestDto) {

    }
}