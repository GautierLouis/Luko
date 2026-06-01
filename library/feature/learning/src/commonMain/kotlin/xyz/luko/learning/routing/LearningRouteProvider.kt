package xyz.luko.learning.routing

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import xyz.luko.learning.builder.SessionBuilderScreen
import xyz.luko.learning.congratulation.EndOfSessionRouterScreen
import xyz.luko.learning.list.SessionListScreen
import xyz.luko.learning.session.ui.SessionScreen
import xyz.luko.navigation.AppRoute

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun EntryProviderScope<NavKey>.learningScreens() {

    // Public route
    entry<AppRoute.LearningRoute.NewSessionRoute> { SessionBuilderScreen() }

    entry<AppRoute.LearningRoute.PracticeCharacterRoute> { TODO() }

    entry<AppRoute.LearningRoute.SessionListRoute> { SessionListScreen(it) }

    // Private route
    entry<LearningInternalRoute.SessionRoute> { SessionScreen(it) }

    entry<LearningInternalRoute.CongratulationRoute> { EndOfSessionRouterScreen() }
}
