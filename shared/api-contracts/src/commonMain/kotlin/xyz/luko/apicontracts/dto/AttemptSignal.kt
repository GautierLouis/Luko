package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class AttemptSignal(
    val characterCode: Int,
    val strokes: List<StrokeDto>,
    val referenceMedians: List<StrokeDto>,
    val recognitionResult: RecognitionResult,
    val resetCount: Int,
    val durationMs: Long,
    val complexityFactor: Double,
    val practiceMode: PracticeMode,
    val fsrsState: FsrsState? = null,
)
