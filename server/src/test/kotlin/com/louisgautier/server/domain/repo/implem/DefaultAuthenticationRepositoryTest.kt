package com.louisgautier.server.domain.repo.implem

import com.louisgautier.apicontracts.dto.RegisterDeviceRequestDto
import com.louisgautier.server.ServerConfig
import com.louisgautier.server.domain.model.UserEntity
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.auth.user.UserSession
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DefaultAuthenticationRepositoryTest {

    private val client = mockk<SupabaseClient>(relaxed = true)
    private val userRepository = mockk<DefaultUserRepository>(relaxed = true)
    private val config = mockk<ServerConfig>(relaxed = true)
    private val auth = mockk<Auth>(relaxed = true)

    private lateinit var repository: DefaultAuthenticationRepository

    @Before
    fun setup() {
        mockkStatic("io.github.jan.supabase.auth.AuthKt")
        every { client.auth } returns auth
        repository = DefaultAuthenticationRepository(client, userRepository, config)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `registerAnonymously should refresh session if user exists`() = runBlocking {
        val device = RegisterDeviceRequestDto("inst-id", "fcm-token")
        val existingUser = UserEntity(
            installationId = "inst-id",
            supabaseUserId = "sup-id",
            supabaseRefreshToken = "ref-token",
            fcmToken = "old-fcm",
            createdAt = "",
            updatedAt = ""
        )
        val session = mockk<UserSession>()
        val user = mockk<UserInfo>()
        every { user.id } returns "sup-id"
        every { session.user } returns user
        every { session.accessToken } returns "access"
        every { session.refreshToken } returns "new-ref"
        every { session.expiresIn } returns 3600L

        coEvery { userRepository.getUserByInstallationId("inst-id") } returns existingUser
        coEvery { auth.refreshSession(any()) } returns session

        val result = repository.registerAnonymously(device)

        assertTrue(result.isSuccess)
        val response = result.getOrThrow()
        assertEquals("sup-id", response.user.id)
        assertEquals("access", response.session.accessToken)

        coVerify { userRepository.updateFcm("inst-id", "fcm-token") }
        coVerify { userRepository.updateSession("sup-id", "new-ref") }
    }

    @Test
    fun `registerAnonymously should return failure if refreshSession fails for existing user`() =
        runBlocking {
            val device = RegisterDeviceRequestDto("inst-id", "fcm-token")
            val existingUser = UserEntity(
                installationId = "inst-id",
                supabaseUserId = "sup-id",
                supabaseRefreshToken = "ref-token",
                fcmToken = "old-fcm",
                createdAt = "",
                updatedAt = ""
            )

            coEvery { userRepository.getUserByInstallationId("inst-id") } returns existingUser
            coEvery { auth.refreshSession(any()) } throws Exception("Refresh failed")

            val result = runCatching { repository.registerAnonymously(device) }

            assertTrue(result.isFailure)
            assertEquals("Refresh failed", result.exceptionOrNull()?.message)
        }

    @Test
    fun `refreshSession should work correctly`() = runBlocking {
        val session = mockk<UserSession>()
        val user = mockk<UserInfo>()
        every { user.id } returns "sup-id"
        every { session.user } returns user
        every { session.accessToken } returns "access"
        every { session.refreshToken } returns "new-ref"
        every { session.expiresIn } returns 3600L

        val existingUser = UserEntity(
            installationId = "inst-id",
            supabaseUserId = "sup-id",
            supabaseRefreshToken = "old-ref",
            fcmToken = "fcm",
            createdAt = "",
            updatedAt = ""
        )

        coEvery { auth.refreshSession("old-ref") } returns session
        coEvery { userRepository.getUserBySupabaseId("sup-id") } returns existingUser

        val result = repository.refreshSession("old-ref")

        assertTrue(result.isSuccess)
        assertEquals("sup-id", result.getOrThrow().user.id)
        coVerify { userRepository.updateSession("sup-id", "new-ref") }
    }

    @Test
    fun `refreshSession should return failure if user not found`() = runBlocking {
        val session = mockk<UserSession>()
        val user = mockk<UserInfo>()
        every { user.id } returns "sup-id"
        every { session.user } returns user

        coEvery { auth.refreshSession("old-ref") } returns session
        coEvery { userRepository.getUserBySupabaseId("sup-id") } returns null

        val result = repository.refreshSession("old-ref")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalStateException)
    }

    @Test
    fun `updateFcm should call userRepository`() = runBlocking {
        val device = RegisterDeviceRequestDto("inst-id", "new-fcm")
        repository.updateFcm(device)
        coVerify { userRepository.updateFcm("inst-id", "new-fcm") }
    }
}
