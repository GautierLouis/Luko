package xyz.luko.recognition

interface CharacterRecognizer {
    suspend fun ensureReady(): Result<Unit>
    suspend fun recognize(strokes: List<RecognizableStroke>): Result<RecognitionResult>
}


data class RecognizablePoint(val x: Float, val y: Float, val timestampMs: Long)
data class RecognizableStroke(val points: List<RecognizablePoint>)

data class RecognitionResult(
    val candidates: List<String>,
)
