package xyz.luko.app.app

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import xyz.luko.auth.AuthRepository
import xyz.luko.domain.repository.SettingTheme
import xyz.luko.domain.repository.UserRepository
import xyz.luko.firebase.FirebaseManager
import xyz.luko.firebase.RemoteConfigFlags
import xyz.luko.firebase.RemoteConfigManager
import xyz.luko.tracking.TrackingEvent
import xyz.luko.utils.AppConfig
import xyz.luko.utils.Flavor
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class AppViewModelTest {

    private fun buildViewModel(
        flavor: Flavor = Flavor.DEV,
        themeFlow: MutableStateFlow<SettingTheme> = MutableStateFlow(SettingTheme.Default),
    ) = AppViewModel(
        firebaseManager = FakeFirebaseManager(),
        authRepository = FakeAuthRepository(),
        remoteConfigManager = FakeRemoteConfigManager(),
        userRepository = FakeUserRepository(themeFlow = themeFlow),
        appConfig = AppConfig(
            platform = "Test",
            flavor = flavor,
            versionName = "1.0.0",
            versionCode = "100",
        )
    )

    class FakeFirebaseManager : FirebaseManager {
        override fun initialize() {}
        override suspend fun getFCMToken() = ""
        override suspend fun getInstallationId() = ""
        override fun logEvent(event: TrackingEvent) {}
        override fun setUserId(userId: String) {}
        override fun setUserProperty(name: String, value: String) {}
        override fun fetchRemoteConfig() {}
    }

    class FakeAuthRepository : AuthRepository {
        override suspend fun registerAnonymously(): Result<Unit> = Result.success(Unit)
        override suspend fun registerNewToken(token: String): Result<Unit> = Result.success(Unit)
    }

    class FakeRemoteConfigManager : RemoteConfigManager {
        override val flags: SharedFlow<RemoteConfigFlags> = MutableStateFlow(RemoteConfigFlags())
        override val synchronizedFlags: RemoteConfigFlags = RemoteConfigFlags()
        override fun register(flags: RemoteConfigFlags) {}
        override fun <T> observe(selector: (RemoteConfigFlags) -> T): Flow<T> = flow { }
        override val completed = MutableStateFlow(false)
    }

    class FakeUserRepository(
        private val themeFlow: MutableStateFlow<SettingTheme> = MutableStateFlow(SettingTheme.Default)
    ) : UserRepository {
        override suspend fun getTheme(): SettingTheme = themeFlow.value
        override fun observeTheme(): Flow<SettingTheme> = themeFlow
        override suspend fun setTheme(theme: SettingTheme) {}
    }

    @Test
    fun `initial state reflects appConfig values`() = runTest {
        val viewModel = buildViewModel(Flavor.PROD)
        assertEquals(false, viewModel.state.value.showFlavorBanner)
        assertEquals(Flavor.PROD, viewModel.state.value.flavor)
    }

    @Test
    fun `theme updates when repository emits new theme`() = runTest {
        val themeFlow = MutableStateFlow(SettingTheme.Default)
        val viewModel = buildViewModel(themeFlow = themeFlow)

        themeFlow.emit(SettingTheme.Day)
        advanceUntilIdle()

        assertEquals(SettingTheme.Day, viewModel.state.value.theme)
    }
}