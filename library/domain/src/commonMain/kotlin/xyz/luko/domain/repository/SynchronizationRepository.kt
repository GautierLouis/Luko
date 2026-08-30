package xyz.luko.domain.repository

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import xyz.luko.recognition.CharacterRecognizer


sealed interface DownloadState {
    data object Idle : DownloadState
    data object Checking : DownloadState
    data object Downloading : DownloadState
    data object Downloaded : DownloadState
    data class Failed(val error: Throwable) : DownloadState

    val isSyncing
        get() = this is Checking || this is Downloading
    val canSync
        get() = this is Idle || this is Checking || this is Failed
}

interface SynchronizationRepository {
    val state: SharedFlow<DownloadState>
    suspend fun start()
    suspend fun retry()
}

class DefaultSynchronizationRepository(
    private val characterRecognizer: CharacterRecognizer,
) : SynchronizationRepository {

    override val state: SharedFlow<DownloadState>
        field = MutableSharedFlow(
            replay = 1,
            extraBufferCapacity = 4,
            onBufferOverflow = BufferOverflow.SUSPEND
        )

    private var lastState: DownloadState = DownloadState.Idle

    override suspend fun start() {
        if (shouldDownload()) {
            downloadRecognitionModel()
        } else {
            emitState(DownloadState.Downloaded)
        }
    }

    override suspend fun retry() {
        downloadRecognitionModel()
    }

    private suspend fun shouldDownload(): Boolean {
        emitState(DownloadState.Checking)

        //nb: Takes a while to reply on Android
        return characterRecognizer.needsDownload()
    }

    private suspend fun downloadRecognitionModel() {
        if (!lastState.canSync) return

        emitState(DownloadState.Downloading)

        characterRecognizer.download()
            .onSuccess { emitState(DownloadState.Downloaded) }
            .onFailure { e -> emitState(DownloadState.Failed(e)) }
    }

    private fun emitState(newState: DownloadState) {
        lastState = newState
        state.tryEmit(newState)
    }
}
