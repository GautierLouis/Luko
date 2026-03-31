package com.louisgautier.domain.model

import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class Session(
    val id: Int = 0,
    val date: Instant,
    val duration: Duration,
    val difficulty: Difficulty,
    val questionsCount: Int,
    val score: Int,
)