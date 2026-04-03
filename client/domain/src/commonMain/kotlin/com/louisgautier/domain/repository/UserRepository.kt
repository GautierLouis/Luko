package com.louisgautier.domain.repository

import com.louisgautier.preferences.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach


enum class SettingTheme {
    Default,
    Night,
    Day,
}

interface UserRepository {
    suspend fun getTheme(): SettingTheme
    fun observeTheme(): Flow<SettingTheme>
    suspend fun setTheme(theme: SettingTheme)
}

internal class DefaultUserRepository(
    private val preferences: AppPreferences
) : UserRepository {

    override suspend fun getTheme(): SettingTheme {
        val stored = preferences.getTheme()
        val theme = stored.toSettingTheme()
        if (stored == null) preferences.setTheme(theme.name)
        return theme
    }

    override fun observeTheme(): Flow<SettingTheme> = preferences.observeTheme()
        .onEach { if (it == null) preferences.setTheme(SettingTheme.Default.name) }
        .map { it.toSettingTheme() }

    private fun String?.toSettingTheme(): SettingTheme =
        this?.let { runCatching { SettingTheme.valueOf(it) }.getOrNull() }
            ?: SettingTheme.Default

    override suspend fun setTheme(theme: SettingTheme) {
        preferences.setTheme(theme.name)
    }
}