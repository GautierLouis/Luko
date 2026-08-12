package xyz.luko.fsrscore.internal

import xyz.luko.apicontracts.dto.PracticeMode

internal object CharacterDurationUseCase {

    private const val MS_PER_STROKE = 900L // To adjust after having data
    private const val BASE_OVERHEAD_MS = 1500L // To adjust after having data
    private const val EASY_MODE_MULTIPLIER = 0.85 // To adjust after having data
    private const val MEDIUM_MODE_MULTIPLIER = 1.0 // To adjust after having data
    private const val HARD_MODE_MULTIPLIER = 1.25 // To adjust after having data
    private const val COMPLEXITY_WEIGHT = 0.5 // To adjust after having data

    fun calculate(
        strokeCount: Int,
        practiceMode: PracticeMode,
        complexityFactor: Double,
    ): Long {

        val multiplier = when (practiceMode) {
            PracticeMode.EASY -> EASY_MODE_MULTIPLIER
            PracticeMode.MEDIUM -> MEDIUM_MODE_MULTIPLIER
            PracticeMode.HARD -> HARD_MODE_MULTIPLIER
        }

        val baseDuration = BASE_OVERHEAD_MS + strokeCount * MS_PER_STROKE * multiplier
        val complexityAdjusted = baseDuration * (1.0 + complexityFactor * COMPLEXITY_WEIGHT)

        return complexityAdjusted.toLong()
    }
}
