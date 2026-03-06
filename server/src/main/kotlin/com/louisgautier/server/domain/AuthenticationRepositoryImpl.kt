package com.louisgautier.server.domain

import com.louisgautier.apicontracts.dto.RegisterDeviceRequestDto
import com.louisgautier.apicontracts.dto.UserAuthMethodProvider
import com.louisgautier.apicontracts.dto.UserInfoJson
import com.louisgautier.apicontracts.dto.UserMetadata
import com.louisgautier.apicontracts.dto.UserRegistrationResponseJson
import com.louisgautier.apicontracts.dto.UserTokenJson
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.user.UserSession

internal class AuthenticationRepositoryImpl(
    private val client: SupabaseClient,
    private val userRepository: UserRepository,
) : AuthenticationRepository {

    private fun buildUserResponse(
        session: UserSession,
        device: RegisterDeviceRequestDto
    ): UserRegistrationResponseJson {
        return UserRegistrationResponseJson(
            user = UserInfoJson(
                id = session.user!!.id,
                provider = listOfNotNull(UserAuthMethodProvider.ANONYMOUS),
            ),
            session = UserTokenJson(
                accessToken = session.accessToken,
                refreshToken = session.refreshToken,
                expiresIn = session.expiresIn,
            ),
            metadata = UserMetadata(
                installationID = device.installationId,
                fcmToken = device.fcmToken,
            ),
        )
    }

    override suspend fun registerAnonymously(device: RegisterDeviceRequestDto): Result<UserRegistrationResponseJson> {
        val existingUser = userRepository.getUserByInstallationId(device.installationId)

        if (existingUser != null) {

            val session = client.auth.currentSessionOrNull()
                ?: client.auth.refreshSession(existingUser.supabaseRefreshToken)

            userRepository.updateFcm(
                installationId = existingUser.installationId,
                fcmToken = device.fcmToken
            )
            userRepository.updateSession(
                supabaseUserId = existingUser.supabaseUserId,
                refreshToken = session.refreshToken
            )

            return Result.success(buildUserResponse(session, device))
        }


        return runCatching {
            client.auth.signInAnonymously()
            val session = client.auth.currentSessionOrNull()
                ?: throw IllegalStateException("No session after signInAnonymously")

            userRepository.create(
                installationId = device.installationId,
                supabaseUserId = session.user!!.id,
                supabaseUserRefreshToken = session.refreshToken,
                fcmToken = device.fcmToken,
            )

            buildUserResponse(session, device)
        }
    }

    override suspend fun refreshSession(refreshToken: String): Result<UserRegistrationResponseJson> {
        return runCatching {
            val session = client.auth.refreshSession(refreshToken = refreshToken)

            val existingUser = userRepository.getUserBySupabaseId(session.user!!.id)
                ?: throw IllegalStateException("No user found for supabaseUserId: ${session.user!!.id}")

            userRepository.updateSession(
                supabaseUserId = existingUser.supabaseUserId,
                refreshToken = session.refreshToken
            )

            buildUserResponse(
                session = session,
                device = RegisterDeviceRequestDto(
                    installationId = existingUser.installationId,
                    fcmToken = existingUser.fcmToken
                )
            )
        }
    }

    override suspend fun updateFcm(device: RegisterDeviceRequestDto) {
        userRepository.updateFcm(
            installationId = device.installationId,
            fcmToken = device.fcmToken
        )

    }
}