package xyz.luko.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
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
}
