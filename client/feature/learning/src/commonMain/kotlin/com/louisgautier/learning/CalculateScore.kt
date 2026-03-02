package com.louisgautier.learning

import com.louisgautier.domain.model.CharacterFrequencyLevel
import com.louisgautier.domain.model.Dictionary
import com.louisgautier.domain.model.Difficulty

object ScoreDefault {
    const val MULTIPLIER_EASY = 1f
    const val MULTIPLIER_MEDIUM = 1.5f
    const val MULTIPLIER_HARD = 2f

    const val LEVEL_COMMON_BASE_POINT = 10
    const val LEVEL_FREQUENT_BASE_POINT = 15
    const val LEVEL_STANDARD_BASE_POINT = 20
    const val LEVEL_EXTENDED_BASE_POINT = 25

    const val MAX_TIME_FOR_COMMON = 5
    const val MAX_TIME_FOR_FREQUENT = 8
    const val MAX_TIME_FOR_STANDARD = 12
    const val MAX_TIME_FOR_EXTENDED = 20

    const val BASE_MIN_POINT = 50
    const val BASE_MAX_POINT = 1000

    const val TIME_MIN_POINT = 0
    const val TIME_MAX_POINT = 1000
}

class CalculateScore() {

    private val basePointsPerLevel = CharacterFrequencyLevel.validEntry.associateWith {
        when (it) {
            CharacterFrequencyLevel.COMMON -> ScoreDefault.LEVEL_COMMON_BASE_POINT
            CharacterFrequencyLevel.FREQUENT -> ScoreDefault.LEVEL_FREQUENT_BASE_POINT
            CharacterFrequencyLevel.STANDARD -> ScoreDefault.LEVEL_STANDARD_BASE_POINT
            CharacterFrequencyLevel.EXTENDED -> ScoreDefault.LEVEL_EXTENDED_BASE_POINT
            else -> null
        }
    }

    private val timePointPerLevel = CharacterFrequencyLevel.validEntry.associateWith {
        when (it) {
            CharacterFrequencyLevel.COMMON -> ScoreDefault.MAX_TIME_FOR_COMMON
            CharacterFrequencyLevel.FREQUENT -> ScoreDefault.MAX_TIME_FOR_FREQUENT
            CharacterFrequencyLevel.STANDARD -> ScoreDefault.MAX_TIME_FOR_STANDARD
            CharacterFrequencyLevel.EXTENDED -> ScoreDefault.MAX_TIME_FOR_EXTENDED
            else -> 0
        }
    }

    fun calculate(
        questions: List<Dictionary>,
        difficulty: Difficulty,
        timeElapsed: Long
    ): Int {

        val difficultyMultiplier = when (difficulty) {
            Difficulty.EASY -> ScoreDefault.MULTIPLIER_EASY
            Difficulty.MEDIUM -> ScoreDefault.MULTIPLIER_MEDIUM
            Difficulty.HARD -> ScoreDefault.MULTIPLIER_HARD
        }

        val baseSum = questions.sumOf {
            val basePointsPerLevel = basePointsPerLevel[it.level]!!
            val basePoint = basePointsPerLevel * difficultyMultiplier
            basePoint.toInt()
        }

        val maxTime = questions.sumOf {
            val timePointPerLevel = timePointPerLevel[it.level]!!
            val timePoint = timePointPerLevel * difficultyMultiplier
            timePoint.toInt() * 1_000
        }

        val timePoint = calculateTimeScore(timeElapsed, maxTime.toLong())

        return baseSum + timePoint
    }

    private fun calculateTimeScore(
        actualTimeSpent: Long,
        maxTimeForSession: Long,
    ): Int {

        // Calculate time efficiency ratio
        // If actual time <= expected time: full points
        // If actual time > expected time: decreasing points
        val timeRatio = maxTimeForSession.toFloat() / actualTimeSpent.toFloat()

        // Score calculation:
        // - Perfect (actual <= expected): 1000 points
        // - 1.5x expected time: 500 points
        // - 2x expected time: 250 points
        // - 3x+ expected time: 0 points
        val timeScore = when {
            timeRatio >= 1.0f -> ScoreDefault.TIME_MAX_POINT
            timeRatio >= 0.67f -> (ScoreDefault.TIME_MAX_POINT * (timeRatio * 1.5f - 0.5f)).toInt()
            timeRatio >= 0.5f -> (ScoreDefault.TIME_MAX_POINT * (timeRatio * 2f - 1f) * 0.5f).toInt()
            timeRatio >= 0.33f -> (ScoreDefault.TIME_MAX_POINT * (timeRatio * 3f - 1f) * 0.25f).toInt()
            else -> 0
        }

        return timeScore.coerceIn(0, ScoreDefault.TIME_MAX_POINT)
    }
}