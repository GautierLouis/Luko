package xyz.luko.server.domain.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.dao.id.EntityID

data class SessionRow(
    val userId: EntityID<Int>,
    val date: String,
    val offset: String,
    val duration: Long,
    val difficulty: String,
    val questionsCount: Int,
    val accuracy: Double,
    val responses: List<SessionResponseRow>
)

@Serializable
data class SessionResponseRow(
    val characterCode: Int,
    val strokes: String,
    val recognitionResult: String,
    val resetCount: Int,
    val durationMs: Long,
    val practiceMode: String,
    val comparisonResult: StrokeComparisonResultRow
)

@Serializable
data class StrokeComparisonResultRow(
    val overallAccuracy: Float,
    val strokeAccuracies: List<Float>,
    val orderAccuracy: Float,
    val details: ComparisonDetailsRow,
)

@Serializable
data class ComparisonDetailsRow(
    val pathSimilarity: Float,
    val startPointAccuracy: Float,
    val endPointAccuracy: Float,
    val directionAccuracy: Float,
)
