package xyz.luko.learning

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import xyz.luko.learning.builder.SessionBuilderScreen
import xyz.luko.learning.congratulation.levelup.LevelUpScreen
import xyz.luko.learning.congratulation.stats.CongratulationScreen
import xyz.luko.learning.congratulation.streak.StreakRefreshScreen
import xyz.luko.learning.session.ui.SessionScreen
import xyz.luko.ui.navigation.AppRoute

fun EntryProviderScope<NavKey>.learningRoutes() {
    entry<AppRoute.Learning.BuildSession> { SessionBuilderScreen() }
    entry<AppRoute.Learning.StartSession> { SessionScreen(it) }
    entry<AppRoute.Learning.StreakUp> { StreakRefreshScreen(it) }
    entry<AppRoute.Learning.Congratulation> { CongratulationScreen(it) }
    entry<AppRoute.Learning.LevelUp> { LevelUpScreen(it) }
    entry<AppRoute.Learning.PracticeCharacter> { TODO() }
}


