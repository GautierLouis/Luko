package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class UserDto(
    val id: String,
    val fcmToken: String?,
    val platform: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)


@Serializable
data class ReviewSessionRequestDto(
    val attempts: List<CharacterAttemptDto>
)

@Serializable
data class CharacterAttemptDto(
    val characterCode: Int,
    val correct: Boolean,              // final pass/fail, still the thing SM-2 cares about
    val recognitionConfidence: Float? = null,   // ML Kit confidence score, nullable until you wire it up
    val strokeOrderCorrect: Boolean? = null,
    val strokeCountCorrect: Boolean? = null,
)

@Serializable
enum class MasteryTier {
    TIER_1, TIER_2, TIER_3, TIER_4, TIER_5
}

@Serializable
data class ReviewSessionResultDto(
    val tierUps: Map<MasteryTier, List<Int>>, // characters that leveled up INTO this tier
    val streak: Int? = null
)
