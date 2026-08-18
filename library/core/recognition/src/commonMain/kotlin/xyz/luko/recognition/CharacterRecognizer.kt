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


fun classifyRecognition(expectedCharacter: String, candidates: List<String>): RecognitionResult {
    val rank = candidates.indexOf(expectedCharacter) // 0-indexed, -1 if absent
    return when (rank) {
        0 -> RecognitionResult.SUCCESS   // 1st result
        in 1..2 -> RecognitionResult.PARTIAL // 2nd-3rd result
        else -> RecognitionResult.FAILURE          // absent, or 4th+
    }
}
