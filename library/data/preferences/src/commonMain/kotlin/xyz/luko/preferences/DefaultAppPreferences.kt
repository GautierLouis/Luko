package xyz.luko.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

internal class DefaultAppPreferences(
    private val store: DataStore<Preferences>,
) : AppPreferences {
    companion object {
        private val USER_ID = stringPreferencesKey("user_id")
        private val KEY_FCM_TOKEN = stringPreferencesKey("fcm_token")
        private val KEY_USER_THEME = stringPreferencesKey("theme")

        private val KEY_OB_STATE = booleanPreferencesKey("onboarding_state")
        private val KEY_OB_SEEN_KEYS = stringSetPreferencesKey("onboarding_seen_keys")

        private val KEY_STREAK = stringPreferencesKey("user_streak")
    }

    override suspend fun setUserId(id: String) {
        store.edit { it[USER_ID] = id }
    }

    override suspend fun setFcmToken(token: String) {
        store.edit { it[KEY_FCM_TOKEN] = token }
    }

    override suspend fun getUserId(): String? = store.data.first()[USER_ID]

    override suspend fun hasUserId(): Boolean = store.data.first()[USER_ID] != null

    override suspend fun getFcmToken(): String? = store.data.first()[KEY_FCM_TOKEN]

    override suspend fun deleteUser() {
        store.edit { settings -> settings.remove(USER_ID) }
    }

    override fun observeTheme(): Flow<String?> =
        store.data.map { preferences -> preferences[KEY_USER_THEME] }

    override suspend fun setTheme(theme: String) {
        store.edit { it[KEY_USER_THEME] = theme }
    }

    override suspend fun getTheme(): String? = observeTheme().firstOrNull()

    // ONBOARDING
    override fun observeIsOnboardingActivated(): Flow<Boolean> =
        store.data.map { preferences -> preferences[KEY_OB_STATE] ?: false }

    override suspend fun setOnboardingState(enable: Boolean) {
        store.edit { it[KEY_OB_STATE] = enable }
    }

    override fun observeSeenKeys(): Flow<Set<String>> =
        store.data.map { preferences -> preferences[KEY_OB_SEEN_KEYS].orEmpty() }

    override suspend fun getSeenKeys(): Set<String> =
        observeSeenKeys().first()

    override suspend fun setKeySeen(keys: Set<String>) {
        store.edit { it[KEY_OB_SEEN_KEYS] = keys }
    }

    // STREAK
    override fun observeStreak(): Flow<String?> =
        store.data.map { preferences -> preferences[KEY_STREAK] }

    override suspend fun getStreak(): String? =
        observeStreak().first()

    override suspend fun updateStreak(str: String) {
        store.edit { it[KEY_STREAK] = str }
    }
}
