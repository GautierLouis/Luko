package xyz.luko.onboarding

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import xyz.luko.onboarding.screens.OnboardingViewModel

val onboardingModule =
    module {
        viewModelOf(::OnboardingViewModel)
    }
