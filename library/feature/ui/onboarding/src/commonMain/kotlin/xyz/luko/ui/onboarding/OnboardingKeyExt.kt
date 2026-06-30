package xyz.luko.ui.onboarding


import xyz.luko.ui.designsystem.onboarding.OnboardingKey

fun OnboardingKey.title() = when (this) {
    OnboardingKey.HOME_MENU -> "Lesson"
    OnboardingKey.OVERALL_STATS -> "Statistics"
}

fun OnboardingKey.desc() = when (this) {
    OnboardingKey.HOME_MENU -> "Press here to start a new Lesson"
    OnboardingKey.OVERALL_STATS -> "See your progress"
}

fun OnboardingKey.action() = when (this) {
    OnboardingKey.HOME_MENU -> "Got it!"
    OnboardingKey.OVERALL_STATS -> "Next"
}

