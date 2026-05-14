package xyz.luko.server.mock

import xyz.luko.apicontracts.dto.RegisterDeviceRequestDto
import xyz.luko.apicontracts.dto.UserAuthMethodProvider
import xyz.luko.apicontracts.dto.UserInfoJson
import xyz.luko.apicontracts.dto.UserMetadata
import xyz.luko.apicontracts.dto.UserRegistrationResponseJson
import xyz.luko.apicontracts.dto.UserTokenJson
import xyz.luko.server.domain.repo.AuthenticationRepository

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