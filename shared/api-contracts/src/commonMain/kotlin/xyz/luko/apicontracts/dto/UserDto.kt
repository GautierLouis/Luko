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
    val levels: Map<Int, List<Int>>,
    val strokeComparison: List<StrokeComparisonResultDto>
)

@Serializable
data class StrokeComparisonResultDto(
    val overallAccuracy: Float,
    val strokeAccuracies: List<Float>,
    val orderAccuracy: Float,
    val details: ComparisonDetailsDto,
)

@Serializable
data class ComparisonDetailsDto(
    val pathSimilarity: Float,
    val startPointAccuracy: Float,
    val endPointAccuracy: Float,
    val directionAccuracy: Float,
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
