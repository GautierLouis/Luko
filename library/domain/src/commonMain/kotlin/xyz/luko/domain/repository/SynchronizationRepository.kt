package xyz.luko.domain.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import xyz.luko.recognition.CharacterRecognizer


sealed interface DownloadState {
    data object Idle : DownloadState
    data object Downloading : DownloadState
    data object Downloaded : DownloadState
    data class Failed(val error: Throwable) : DownloadState
}

interface SynchronizationRepository {
    val state: SharedFlow<DownloadState>
    fun start()
    fun retry()
}

class DefaultSynchronizationRepository(
    private val characterRecognizer: CharacterRecognizer,
    private val scope: CoroutineScope, // app-scoped, injected — outlives any single VM
) : SynchronizationRepository {

    private val _state = MutableSharedFlow<DownloadState>(
        replay = 1,
        extraBufferCapacity = 4,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    override val state: SharedFlow<DownloadState> = _state.asSharedFlow()
    private var lastState: DownloadState = DownloadState.Idle

    private var job: Job? = null

    init {
        _state.tryEmit(lastState) // seed the replay cache with the initial value
    }


    override fun start() {
        if (job?.isActive == true) return // already running/observed — don't restart
        downloadRecognitionModel()
    }

    override fun retry() {
        job?.cancel()
        downloadRecognitionModel()
    }

    private fun downloadRecognitionModel() {
        job = scope.launch {
            if (lastState is DownloadState.Downloaded) return@launch

            val needsDownload = characterRecognizer.needsDownload()
            if (!needsDownload) {
                emitState(DownloadState.Downloaded)
                return@launch
            }

            emitState(DownloadState.Downloading)

            characterRecognizer.download()
                .onSuccess { emitState(DownloadState.Downloaded) }
                .onFailure { e -> emitState(DownloadState.Failed(e)) }
        }
    }

    private fun emitState(newState: DownloadState) {
        lastState = newState
        _state.tryEmit(newState)
    }
}
