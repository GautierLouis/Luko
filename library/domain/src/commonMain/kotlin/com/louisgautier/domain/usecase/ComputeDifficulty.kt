package com.louisgautier.domain.usecase

import com.louisgautier.domain.model.DifficultyLevel

internal class ComputeDifficulty {
    fun average(difficulties: List<String>): DifficultyLevel? {
        val average =
            difficulties
                .takeIf { it.isNotEmpty() }
                ?.map {
                    when (DifficultyLevel.valueOf(it)) {
                        DifficultyLevel.EASY -> 1
                        DifficultyLevel.MEDIUM -> 2
                        DifficultyLevel.HARD -> 3
                    }
                }?.average()

        return when {
            average == null -> null
            average <= 1.5 -> DifficultyLevel.EASY
            average <= 2.5 -> DifficultyLevel.MEDIUM
            else -> DifficultyLevel.HARD
        }
    }
}
