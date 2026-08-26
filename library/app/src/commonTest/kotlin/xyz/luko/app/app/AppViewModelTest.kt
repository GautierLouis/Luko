package xyz.luko.app.app

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import xyz.luko.domain.model.ReviewResult
import xyz.luko.domain.model.SessionResponse
import xyz.luko.domain.model.SettingTheme
import xyz.luko.domain.repository.AppStartUseCase
import xyz.luko.domain.repository.AuthRepository
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
        remoteConfigManager = FakeRemoteConfigManager(),
        userRepository = FakeUserRepository(themeFlow = themeFlow),
        appConfig =
            AppConfig(
                platform = "Test",
                flavor = flavor,
                versionName = "1.0.0",
                versionCode = "100",
            ),
        appStartViewModel = AppStartUseCase(
            FakeAuthRepository(), FakeUserRepository(),
            object : FirebaseManager {
                override fun initialize() {

                }

                override suspend fun registerAnonymously(): Result<String> {
                    TODO("Not yet implemented")
                }

                override suspend fun getIdToken(forceRefresh: Boolean): Result<String> {
                    TODO("Not yet implemented")
                }

                override suspend fun getFCMToken(): Result<String> {
                    TODO("Not yet implemented")
                }

                override fun logEvent(event: TrackingEvent) {
                    TODO("Not yet implemented")
                }

                override fun setUserId(userId: String) {
                    TODO("Not yet implemented")
                }

                override fun setUserProperty(name: String, value: String) {
                    TODO("Not yet implemented")
                }

                override fun fetchRemoteConfig() {
                    TODO("Not yet implemented")
                }
            })

    )

    class FakeFirebaseManager : FirebaseManager {
        override fun initialize() {}
        override suspend fun registerAnonymously(): Result<String> {
            return Result.success("")
        }

        override suspend fun getIdToken(forceRefresh: Boolean): Result<String> {
            return Result.success("")
        }

        override suspend fun getFCMToken() = Result.success("")


        override fun logEvent(event: TrackingEvent) {}

        override fun setUserId(userId: String) {}

        override fun setUserProperty(
            name: String,
            value: String,
        ) {
        }

        override fun fetchRemoteConfig() {}
    }

    class FakeAuthRepository : AuthRepository {
        override suspend fun getUserId(): String? {
            TODO("Not yet implemented")
        }

        override suspend fun registerAnonymously(id: String, fcmToken: String?) {
            TODO("Not yet implemented")
        }

        override suspend fun updateFcm(fcmToken: String?) {
            TODO("Not yet implemented")
        }

        override suspend fun onNewFcmToken(token: String): Result<Unit> {
            TODO("Not yet implemented")
        }

    }

    class FakeRemoteConfigManager : RemoteConfigManager {
        override val flags: SharedFlow<RemoteConfigFlags> = MutableStateFlow(RemoteConfigFlags())
        override val synchronizedFlags: RemoteConfigFlags = RemoteConfigFlags()

        override fun register(flags: RemoteConfigFlags) {}

        override fun <T> observe(selector: (RemoteConfigFlags) -> T): Flow<T> = flow { }

        override val completed = MutableStateFlow(false)
    }

    class FakeUserRepository(
        private val themeFlow: MutableStateFlow<SettingTheme> = MutableStateFlow(SettingTheme.Default),
    ) : UserRepository {
        override suspend fun getTheme(): SettingTheme {
            return themeFlow.value
        }

        override fun observeTheme(): Flow<SettingTheme> {
            TODO("Not yet implemented")
        }

        override suspend fun setTheme(theme: SettingTheme) {
            TODO("Not yet implemented")
        }

        override fun haveSeenOnboarding(): Flow<Boolean> {
            TODO("Not yet implemented")
        }

        override suspend fun setSeenOnboarding(enable: Boolean) {
            TODO("Not yet implemented")
        }

        override fun observeStreak(): Flow<Int> {
            TODO("Not yet implemented")
        }

        override suspend fun getStreak(): Int? {
            TODO("Not yet implemented")
        }

        override suspend fun reviewSession(responses: List<SessionResponse>): Result<ReviewResult> {
            TODO("Not yet implemented")
        }

        override suspend fun getMe(): Result<Unit> {
            TODO("Not yet implemented")
        }

    }

    @Test
    fun `initial state reflects appConfig values`() =
        runTest {
            val viewModel = buildViewModel(Flavor.PROD)
            assertEquals(false, viewModel.state.value.showFlavorBanner)
            assertEquals(Flavor.PROD, viewModel.state.value.flavor)
        }

    @Test
    fun `theme updates when repository emits new theme`() =
        runTest {
            val themeFlow = MutableStateFlow(SettingTheme.Default)
            val viewModel = buildViewModel(themeFlow = themeFlow)

            themeFlow.emit(SettingTheme.Day)
            advanceUntilIdle()

            assertEquals(SettingTheme.Day.name, viewModel.state.value.theme?.name)
        }
}
