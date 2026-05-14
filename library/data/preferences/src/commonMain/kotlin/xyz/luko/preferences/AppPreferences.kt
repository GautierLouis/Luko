package xyz.luko.preferences

import kotlinx.coroutines.flow.Flow

interface AppPreferences {
    suspend fun setUserToken(token: String)

    suspend fun setUserRefreshToken(token: String)

    suspend fun setInstallationId(id: String)

    suspend fun setFcmToken(token: String)

    fun getUserTokenAsFlow(): Flow<String?>

    fun getUserRefreshTokenAsFlow(): Flow<String?>

    fun observeTheme(): Flow<String?>

    suspend fun getUserToken(): String?

    suspend fun getUserRefreshToken(): String?

    suspend fun getInstallationId(): String?

    suspend fun getFcmToken(): String?

    suspend fun removeUserToken()

    //
    suspend fun getTheme(): String?

    suspend fun setTheme(theme: String)
}
