package xyz.luko.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.SessionSettings
import xyz.luko.domain.repository.DownloadState
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.domain.repository.SynchronizationRepository
import xyz.luko.domain.repository.UserRepository
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute
import xyz.luko.utils.AppConfig
import kotlin.time.Duration.Companion.milliseconds

sealed interface NewCard {
    data object Onboarding : NewCard
    data object Dictionary : NewCard
}

@OptIn(FlowPreview::class)
internal class HomeViewModel(
    sessionRepository: SessionRepository,
    private val synchronizationRepository: SynchronizationRepository,
    private val userRepository: UserRepository,
    private val appConfig: AppConfig,
) : ViewModel() {
    data class UIState(
        val lastSession: List<Session> = emptyList(),
        val streakCount: Int = 0,
        val lastSettings: List<SessionSettings> = emptyList(),
        val news: List<NewCard> = emptyList(),
        val syncingState: DownloadState = DownloadState.Idle,
        val isDebug: Boolean = false,
    ) {
        val enableLearn
            get() = syncingState == DownloadState.Downloaded
        val enableLastSession
            get() = lastSession.isNotEmpty()
        val enableNews
            get() = news.isNotEmpty()
        val isSyncing
            get() = syncingState == DownloadState.Downloading || syncingState is DownloadState.Failed
    }

    val state: StateFlow<UIState>
        field = MutableStateFlow(UIState())

    init {

        synchronizationRepository.start()

        combine(
            sessionRepository.getLastSessions(5),
            userRepository.observeStreak(),
            userRepository.haveSeenOnboarding(),
            synchronizationRepository.state,
            sessionRepository.getLastSessionConfiguration(),
        ) { lastSessions, streak, seenOb, syncState, lastSettings ->
            UIState(
                isDebug = !appConfig.isProduction,
                lastSession = lastSessions,
                streakCount = streak,
                lastSettings = lastSettings,
                news = buildList {
                    if (!seenOb) {
                        add(NewCard.Onboarding)
                    }
                    add(NewCard.Dictionary)
                },
                syncingState = syncState,
            )
        }.debounce(100.milliseconds)
            .distinctUntilChanged()
            .onEach { new -> state.update { new } }
            .launchIn(viewModelScope)
    }

    fun event(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.StartOnboarding -> startOnboarding()
            HomeScreenEvent.RetrySync -> restartSync()
        }
    }

    private fun startOnboarding() {
        viewModelScope.launch {
            userRepository.setSeenOnboarding(true)
            AppNavigation.navigate(AppRoute.Onboarding.Home)
        }
    }

    private fun restartSync() {
        synchronizationRepository.retry()
    }
}
