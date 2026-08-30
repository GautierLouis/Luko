package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReviewAttemptRequest(
    val doneAt: String,
    val session: ReviewSessionRequestDto,
)

@Serializable
data class ReviewSessionRequestDto(
    val date: String,
    val offset: String,
    val duration: Long,
    val difficulty: String,
    val questionsCount: Int,
    val responses: List<ReviewResponseRequestDto>
)

@Serializable
data class ReviewResponseRequestDto(
    val characterCode: Int,
    val strokes: List<StrokeDto>,
    val recognitionResult: RecognitionResult,
    val resetCount: Int,
    val durationMs: Long,
    val practiceMode: PracticeMode,
)
