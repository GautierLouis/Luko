package xyz.luko.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import xyz.luko.apicontracts.dto.ReviewAttemptRequest
import xyz.luko.domain.mapper.toDomain
import xyz.luko.domain.mapper.toDto
import xyz.luko.domain.model.ReviewResult
import xyz.luko.domain.model.SessionResponse
import xyz.luko.domain.model.SettingTheme
import xyz.luko.network.interfaces.UserService
import xyz.luko.preferences.AppPreferences
import kotlin.time.Clock

interface UserRepository {
    suspend fun getTheme(): SettingTheme

    fun observeTheme(): Flow<SettingTheme>

    suspend fun setTheme(theme: SettingTheme)

    fun haveSeenOnboarding(): Flow<Boolean>
    suspend fun setSeenOnboarding(enable: Boolean)

    fun observeStreak(): Flow<Int>
    suspend fun getStreak(): Int?

    suspend fun reviewSession(
        responses: List<SessionResponse>
    ): Result<ReviewResult>

    suspend fun getMe(): Result<Unit>
}

internal class DefaultUserRepository(
    private val preferences: AppPreferences,
    private val userService: UserService,
) : UserRepository {
    override suspend fun getTheme(): SettingTheme {
        val stored = preferences.getTheme()
        val theme = stored.toSettingTheme()
        if (stored == null) preferences.setTheme(theme.name)
        return theme
    }

    override fun observeTheme(): Flow<SettingTheme> =
        preferences
            .observeTheme()
            .onEach { if (it == null) preferences.setTheme(SettingTheme.Default.name) }
            .map { it.toSettingTheme() }

    private fun String?.toSettingTheme(): SettingTheme =
        this?.let { runCatching { SettingTheme.valueOf(it) }.getOrNull() }
            ?: SettingTheme.Default

    override suspend fun setTheme(theme: SettingTheme) {
        preferences.setTheme(theme.name)
    }

    override fun haveSeenOnboarding(): Flow<Boolean> {
        return flowOf(false) //preferences.observeIsOnboardingActivated()
    }

    override suspend fun setSeenOnboarding(enable: Boolean) {
        preferences.setOnboardingState(enable)
    }

    override fun observeStreak(): Flow<Int> {
        return preferences.observeStreak().map { raw -> raw ?: 0 }
    }

    override suspend fun getStreak(): Int? {
        return preferences.getStreak()
    }

    override suspend fun reviewSession(
        responses: List<SessionResponse>
    ): Result<ReviewResult> {
        return userService.reviewSession(
            ReviewAttemptRequest(
                doneAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                responses = responses.toDto()
            )
        ).map { it.toDomain() }
    }

    override suspend fun getMe(): Result<Unit> {
        return userService.me()
            .onSuccess {
                preferences.updateStreak(it.currentStreak)
                preferences.updateLevels(Json.encodeToString(it.levels))
            }.map { }
    }
}
