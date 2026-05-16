package xyz.luko.learning.routing

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import xyz.luko.learning.builder.SessionBuilderScreen
import xyz.luko.learning.congratulation.SessionCongratulationScreen
import xyz.luko.learning.session.SessionScreen
import xyz.luko.navigation.AppRoute

fun EntryProviderScope<NavKey>.learningScreens() {
    // Public route
    entry<AppRoute.LearningRoute.NewSessionRoute> { SessionBuilderScreen() }
    entry<AppRoute.LearningRoute.PracticeCharacterRoute> { TODO() }
    // Private route
    entry<LearningInternalRoute.SessionRoute> { SessionScreen() }
    entry<LearningInternalRoute.CongratulationRoute> { SessionCongratulationScreen() }
}
