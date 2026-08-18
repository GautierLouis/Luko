package xyz.luko.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.Session
import xyz.luko.domain.repository.DownloadState
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.domain.repository.SynchronizationRepository
import xyz.luko.domain.repository.UserRepository
import kotlin.time.Duration.Companion.milliseconds

data class LastSessionSettings(
    val difficultyLevel: DifficultyLevel,
    val count: Int,
    val frequencyLevel: List<CharacterFrequencyLevel>
)

sealed interface NewCard {
    data object Onboarding : NewCard
}

@OptIn(FlowPreview::class)
internal class HomeViewModel(
    sessionRepository: SessionRepository,
    private val synchronizationRepository: SynchronizationRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    data class UIState(
        val lastSession: List<Session> = emptyList(),
        val streakCount: Int = 0,
        val lastSettings: List<LastSessionSettings> = emptyList(),
        val news: List<NewCard> = emptyList(),
        val syncingState: DownloadState = DownloadState.Idle,
    ) {
        val enableLearn
            get() = syncingState == DownloadState.Downloaded
        val enableLastSession
            get() = lastSession.isNotEmpty()
        val enableSettings
            get() = lastSettings.isNotEmpty()
        val enableNews
            get() = news.isNotEmpty()
        val isSyncing
            get() = syncingState == DownloadState.Downloading || syncingState is DownloadState.Failed
    }

    private val _state = MutableStateFlow(UIState())
    val state = _state.asStateFlow()

    init {

        synchronizationRepository.start()

        combine(
            sessionRepository.getLastSessions(5),
            userRepository.observeStreak(),
            userRepository.isOnboardingActivated(),
            synchronizationRepository.state
        ) { lastSessions, streak, activeOb, syncState ->
            UIState(
                lastSession = lastSessions,
                streakCount = streak,
                lastSettings = emptyList(),
                news = buildList {
                    add(NewCard.Onboarding)
                },
                syncingState = syncState
            )
        }.debounce(100.milliseconds)
            .distinctUntilChanged()
            .onEach { new -> _state.update { new } }
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
            userRepository.setOnboarding(true)
        }
    }

    private fun restartSync() {
        synchronizationRepository.retry()
    }
}
