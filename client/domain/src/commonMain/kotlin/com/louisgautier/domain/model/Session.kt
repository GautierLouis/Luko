package com.louisgautier.domain.model

import kotlin.time.Duration
import kotlin.time.Instant

data class Session(
    val id: Int = 0,
    val date: Instant,
    val duration: Duration,
    val difficulty: DifficultyLevel,
    val questionsCount: Int,
    val score: Int,
)