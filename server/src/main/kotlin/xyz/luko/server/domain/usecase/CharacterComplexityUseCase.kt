package xyz.luko.server.domain.usecase

import xyz.luko.apicontracts.dto.IdeographicNodeDto
import xyz.luko.apicontracts.dto.StrokeDto
import kotlin.math.hypot


class CharacterComplexityUseCase {

    companion object {
        private const val MAX_STROKES = 33
        private const val MAX_PATH_LENGTH = 6893.68297372555
        private const val MAX_COMPONENTS = 7
    }

    fun calculate(
        decomposition: IdeographicNodeDto,
        medians: List<StrokeDto>
    ): Double {

        val strokeCount = medians.size
        val medianPathLength = computeMedianPathLength(medians)
        val componentCount = decomposition.leafCount()

        val strokeNorm = (strokeCount.toDouble() / MAX_STROKES).coerceIn(0.0, 1.0)
        val pathNorm = (medianPathLength / MAX_PATH_LENGTH).coerceIn(0.0, 1.0)
        val componentNorm = (componentCount.toDouble() / MAX_COMPONENTS).coerceIn(0.0, 1.0)

        return strokeNorm * 0.5 + pathNorm * 0.35 + componentNorm * 0.15
    }

    // sum of Euclidean distances between consecutive points, all strokes
    private fun computeMedianPathLength(medians: List<StrokeDto>): Double =
        medians.sumOf { stroke ->
            stroke.points.zipWithNext { a, b ->
                hypot((b.x - a.x).toDouble(), (b.y - a.y).toDouble())
            }.sum()
        }

    private fun IdeographicNodeDto.leafCount(): Int = when (this) {
        is IdeographicNodeDto.Operator -> children.sumOf { it.leafCount() }
        else -> 1
    }
}
