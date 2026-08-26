package xyz.luko.learning.session

import xyz.luko.domain.model.Stroke
import xyz.luko.recognition.CharacterRecognizer
import xyz.luko.recognition.RecognitionResult
import xyz.luko.recognition.RecognizablePoint
import xyz.luko.recognition.RecognizableStroke

internal class CharacterRecognizedUseCase(
    private val characterRecognizer: CharacterRecognizer
) {

    suspend fun recognize(expectedCharacter: String, strokes: List<Stroke>): RecognitionResult {
        val result = characterRecognizer.recognize(strokes.map {
            RecognizableStroke(it.points.map { p ->
                RecognizablePoint(p.x, p.y, p.timestamp)
            })
        })

        return if (result.isSuccess) {
            classifyRecognition(expectedCharacter, result.getOrThrow())
        } else {
            RecognitionResult.FAILURE
        }

    }

    private fun classifyRecognition(
        expectedCharacter: String,
        candidates: List<String>
    ): RecognitionResult {
        val rank = candidates.indexOf(expectedCharacter) // 0-indexed, -1 if absent
        return when (rank) {
            0 -> RecognitionResult.SUCCESS   // 1st result
            in 1..2 -> RecognitionResult.PARTIAL // 2nd-3rd result
            else -> RecognitionResult.FAILURE          // absent, or 4th+
        }
    }
}
