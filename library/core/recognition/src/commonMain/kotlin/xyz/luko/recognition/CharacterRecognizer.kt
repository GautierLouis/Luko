package xyz.luko.recognition


interface CharacterRecognizer {
    suspend fun needsDownload(): Boolean
    suspend fun download(): Result<Boolean>
    suspend fun recognize(strokes: List<RecognizableStroke>): Result<List<String>>
}

data class RecognizablePoint(val x: Float, val y: Float, val timestampMs: Long)
data class RecognizableStroke(val points: List<RecognizablePoint>)

enum class RecognitionResult {
    SUCCESS,
    PARTIAL,
    FAILURE
}
