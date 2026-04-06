package com.louisgautier.learning

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.louisgautier.designsystem.components.attrs.FrequencyLevel
import com.louisgautier.domain.model.DifficultyLevel
import com.louisgautier.learning.builder.QuestionCount
import com.louisgautier.learning.builder.SessionBuilderScreen
import com.louisgautier.learning.congratulation.SessionCongratulationScreen
import com.louisgautier.learning.session.SessionScreen
import com.louisgautier.navigation.AppRoute
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic


@Serializable
internal sealed interface LearningRoute : NavKey {
    @Serializable
    data class SessionRoute(
        val levels: List<FrequencyLevel>,
        val difficulty: DifficultyLevel,
        val limit: QuestionCount,
    ) : LearningRoute

    @Serializable
    data object CongratulationRoute : LearningRoute
}

@OptIn(ExperimentalSerializationApi::class)
val learningRouteSerializer = SerializersModule {
    polymorphic(NavKey::class) {
        subclassesOfSealed<LearningRoute>()
    }
}

fun EntryProviderScope<NavKey>.learningScreens() {
    // Public route
    entry<AppRoute.LearningRoute.NewSessionRoute> { SessionBuilderScreen() }
    entry<AppRoute.LearningRoute.PracticeCharacterRoute> { TODO() }
    // Private route
    entry<LearningRoute.SessionRoute> { SessionScreen() }
    entry<LearningRoute.CongratulationRoute> { SessionCongratulationScreen() }
}

