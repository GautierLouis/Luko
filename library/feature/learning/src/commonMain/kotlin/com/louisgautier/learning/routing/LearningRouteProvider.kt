package com.louisgautier.learning.routing

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.louisgautier.learning.builder.SessionBuilderScreen
import com.louisgautier.learning.congratulation.SessionCongratulationScreen
import com.louisgautier.learning.session.SessionScreen
import com.louisgautier.navigation.AppRoute

fun EntryProviderScope<NavKey>.learningScreens() {
    // Public route
    entry<AppRoute.LearningRoute.NewSessionRoute> { SessionBuilderScreen() }
    entry<AppRoute.LearningRoute.PracticeCharacterRoute> { TODO() }
    // Private route
    entry<LearningInternalRoute.SessionRoute> { SessionScreen() }
    entry<LearningInternalRoute.CongratulationRoute> { SessionCongratulationScreen() }
}