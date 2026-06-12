package xyz.luko.learning.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import xyz.luko.learning.builder.SessionBuilderScreen
import xyz.luko.learning.congratulation.stats.CongratulationScreen
import xyz.luko.learning.congratulation.streak.StreakRefreshScreen
import xyz.luko.learning.session.ui.SessionScreen
import xyz.luko.ui.navigation.AppRoute

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun EntryProviderScope<NavKey>.learningScreens() {

    // Public route
    entry<AppRoute.LearningRoute.NewSessionRoute> { SessionBuilderScreen() }

    entry<AppRoute.LearningRoute.PracticeCharacterRoute> { TODO() }

    // Private route
    entry<LearningInternalRoute.SessionRoute> { SessionScreen(it) }

    entry<LearningInternalRoute.StreakRoute> { StreakRefreshScreen(it.newValue, it.lastSession) }
    entry<LearningInternalRoute.CongratulationRoute> { CongratulationScreen(it.lastSession) }
}
