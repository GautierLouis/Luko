package xyz.luko.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate
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
import xyz.luko.domain.usecase.StreakPreferences
import xyz.luko.domain.usecase.StreakResult
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

    fun observeStreak(): Flow<StreakResult>
    suspend fun getStreak(): StreakPreferences?
    suspend fun updateStreak(streak: StreakPreferences)

    suspend fun reviewSession(
        difficultyLevel: DifficultyLevel,
        responses: List<SessionResponse>
    ): Result<Boolean>
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

    private val fakeState = MutableStateFlow(false)
    private val fakeSeen = MutableStateFlow<Set<String>>(setOf())

    override fun isOnboardingActivated(): Flow<Boolean> {
        return fakeState.filter { it }
//       return preferences.observeIsOnboardingActivated().filter { it }
    }

    override suspend fun setOnboarding(enable: Boolean) {
        fakeState.update { enable }
//        preferences.setOnboardingState(enable)
    }

    //
    override fun observeKey(): Flow<Set<String>> {
        return fakeSeen
//        return preferenceces.observeSeenKeys()
    }

    override suspend fun setKeySeen(key: String) {
        fakeSeen.update { it + key }
//        val old = preferences
//            .getSeenKeys()
//            .toMutableSet()
//        old.add(key.name)
//        preferences.setKeySeen(old)
    }

    override fun observeStreak(): Flow<StreakResult> {
        return preferences.observeStreak()
            .map { raw ->
                val prefs = raw?.let { Json.decodeFromString<StreakPreferences>(it) }
                    ?: return@map StreakResult(0, false)

                val todayUtc = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
                val lastDate = LocalDate.parse(prefs.lastUpdatedDate)

                StreakResult(
                    streakCount = prefs.streakCount,
                    updatedToday = lastDate == todayUtc
                )
            }
    }

    override suspend fun getStreak(): StreakPreferences? {
        return preferences.getStreak()?.let {
            Json.decodeFromString(it)
        }
    }

    override suspend fun updateStreak(streak: StreakPreferences) {
        preferences.updateStreak(Json.encodeToString(streak))
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
}
