package xyz.luko.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import xyz.luko.domain.model.SettingTheme
import xyz.luko.preferences.AppPreferences

interface UserRepository {
    suspend fun getTheme(): SettingTheme

    fun observeTheme(): Flow<SettingTheme>

    suspend fun setTheme(theme: SettingTheme)

    fun isOnboardingActivated(): Flow<Boolean>
    suspend fun setOnboarding(enable: Boolean)
    suspend fun setKeySeen(key: String)
    fun observeKey(): Flow<Set<String>>
}

internal class DefaultUserRepository(
    private val preferences: AppPreferences,
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
}
