package xyz.luko.preferences

import kotlinx.coroutines.flow.Flow

interface AppPreferences {

    suspend fun setUserId(id: String)
    suspend fun setFcmToken(token: String)
    suspend fun getUserId(): String?
    suspend fun hasUserId(): Boolean
    suspend fun getFcmToken(): String?
    suspend fun deleteUser()

    // Theme
    fun observeTheme(): Flow<String?>
    suspend fun getTheme(): String?
    suspend fun setTheme(theme: String)

    // Onboarding
    fun observeIsOnboardingActivated(): Flow<Boolean>
    suspend fun setOnboardingState(enable: Boolean)
    suspend fun setKeySeen(keys: Set<String>)
    fun observeSeenKeys(): Flow<Set<String>>
    suspend fun getSeenKeys(): Set<String>
}
