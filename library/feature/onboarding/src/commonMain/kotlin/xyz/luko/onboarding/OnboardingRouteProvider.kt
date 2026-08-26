package xyz.luko.onboarding

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import xyz.luko.onboarding.screens.OnboardingLastScreen
import xyz.luko.onboarding.screens.OnboardingScreen
import xyz.luko.ui.navigation.AppRoute

fun EntryProviderScope<NavKey>.onboardingRoutes() {
    entry<AppRoute.Onboarding.Home> { OnboardingScreen() }
    entry<AppRoute.Onboarding.LastPage> { OnboardingLastScreen() }
}
