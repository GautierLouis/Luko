package com.louisgautier.learning

import androidx.navigation3.runtime.NavKey
import com.louisgautier.designsystem.components.attrs.FrequencyLevel
import com.louisgautier.domain.model.DifficultyLevel
import com.louisgautier.learning.builder.QuestionCount
import kotlinx.serialization.Serializable

@Serializable
sealed interface LearningRoute : NavKey

@Serializable
data object StartSessionRoute : LearningRoute

@Serializable
data class SessionRoute(
    val levels: List<FrequencyLevel>,
    val difficulty: DifficultyLevel,
    val limit: QuestionCount,
) : LearningRoute

@Serializable
data object CongratulationRoute : LearningRoute
