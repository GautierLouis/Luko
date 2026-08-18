package xyz.luko.apicontracts.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
enum class RecognitionResult { FAILURE, SUCCESS, PARTIAL }

@Serializable
data class ReviewAttemptRequest(
    val doneAt: LocalDateTime,
    val responses: List<ReviewResponseRequest>
)

@Serializable
data class ReviewResponseRequest(
    val characterCode: Int,
    val strokes: List<StrokeDto>,
    val recognitionResult: RecognitionResult,
    val resetCount: Int,
    val durationMs: Long,
    val practiceMode: PracticeMode,
)

@Serializable
data class AttemptSignal(
    val characterCode: Int,
    val strokes: List<StrokeDto>,
    val rawReferenceMedians: List<List<List<Float>>>,
    val recognitionResult: RecognitionResult,
    val resetCount: Int,
    val durationMs: Long,
    val complexityFactor: Double,
    val practiceMode: PracticeMode,
    val fsrsState: FsrsState? = null,
)

@Serializable
enum class PracticeMode { EASY, MEDIUM, HARD }

@Serializable
data class FsrsState(
    val difficulty: Double,
    val stability: Double,
    val level: Int,
    val lastReviewedAt: Long,
)

@Serializable
data class ReviewResultDto(
    val isStreakUpdated: Boolean,
    val newStreak: Int,
    val hasLevelUp: Boolean,
    val levels: Map<Int, List<Int>>
)

@Serializable
data class UserDto(
    /**
     * FirebaseID, not Exposed ID
     */
    val id: String,
    val fcmToken: String?,
    val platform: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)

@Serializable
data class MeDto(
    val currentStreak: Int,
    val levels: Map<Int, List<Int>>,
)
