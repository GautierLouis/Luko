package xyz.luko.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import xyz.luko.apicontracts.dto.PracticeMode
import xyz.luko.apicontracts.dto.RecognitionResult
import xyz.luko.apicontracts.dto.ReviewAttemptRequest
import xyz.luko.apicontracts.dto.ReviewResponseRequest
import xyz.luko.domain.mapper.toDto
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.SessionResponse
import xyz.luko.domain.model.SettingTheme
import xyz.luko.network.interfaces.UserService
import xyz.luko.preferences.AppPreferences
import kotlin.time.Clock

interface UserRepository {
    suspend fun getTheme(): SettingTheme

    fun observeTheme(): Flow<SettingTheme>

    suspend fun setTheme(theme: SettingTheme)

    fun isOnboardingActivated(): Flow<Boolean>
    suspend fun setOnboarding(enable: Boolean)
    suspend fun setKeySeen(key: String)
    fun observeKey(): Flow<Set<String>>

    fun observeStreak(): Flow<Int>
    suspend fun getStreak(): Int?

    suspend fun reviewSession(
        difficultyLevel: DifficultyLevel,
        responses: List<SessionResponse>
    ): Result<Boolean>

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

    override fun isOnboardingActivated(): Flow<Boolean> {
        return preferences.observeIsOnboardingActivated()
    }

    override suspend fun setOnboarding(enable: Boolean) {
//        preferences.setOnboardingState(enable)
    }

    override fun observeKey(): Flow<Set<String>> {
        return preferences.observeSeenKeys()
    }

    override suspend fun setKeySeen(key: String) {
//        val old = preferences
//            .getSeenKeys()
//            .toMutableSet()
//        old.add(key.name)
//        preferences.setKeySeen(old)
    }

    override fun observeStreak(): Flow<Int> {
        return preferences.observeStreak().map { raw -> raw ?: 1 }
    }

    override suspend fun getStreak(): Int? {
        return preferences.getStreak()
    }

    override suspend fun reviewSession(
        difficultyLevel: DifficultyLevel,
        responses: List<SessionResponse>
    ): Result<Boolean> {
        userService.reviewSession(
            ReviewAttemptRequest(
                doneAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                responses = responses.map { r ->
                    ReviewResponseRequest(
                        characterCode = r.code,
                        strokes = r.strokes.map { it.toDto() },
                        recognitionResult = RecognitionResult.valueOf(r.recognitionResult),
                        resetCount = 0,
                        durationMs = r.strokes.flatMap { it.points }
                            .map { it.timestamp }
                            .let { timestamps ->
                                if (timestamps.isEmpty()) 0L else timestamps.max() - timestamps.min()
                            },
                        practiceMode = when (difficultyLevel) {
                            DifficultyLevel.EASY -> PracticeMode.EASY
                            DifficultyLevel.MEDIUM -> PracticeMode.MEDIUM
                            DifficultyLevel.HARD -> PracticeMode.HARD
                        }
                    )
                }
            )
        )

        return Result.success(true)
    }

    override suspend fun getMe(): Result<Unit> {
        return userService.me()
            .onSuccess {
                preferences.updateStreak(it.currentStreak)
                preferences.updateLevels(Json.encodeToString(it.levels))
            }.map { }
    }
}
