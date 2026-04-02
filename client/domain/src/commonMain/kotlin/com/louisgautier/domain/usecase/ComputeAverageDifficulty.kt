package com.louisgautier.domain.usecase

import com.louisgautier.domain.model.Difficulty


internal class ComputeDifficulty {

    fun average(difficulties: List<String>): Difficulty? {
        val average = difficulties
            .takeIf { it.isNotEmpty() }
            ?.map {
                when (Difficulty.valueOf(it)) {
                    Difficulty.EASY -> 1
                    Difficulty.MEDIUM -> 2
                    Difficulty.HARD -> 3
                }
            }?.average()

        return when {
            average == null -> null
            average <= 1.5 -> Difficulty.EASY
            average <= 2.5 -> Difficulty.MEDIUM
            else -> Difficulty.HARD
        }
    }
}