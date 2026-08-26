package xyz.luko.onboarding.strings

import androidx.compose.runtime.Immutable
import xyz.luko.ui.designsystem.theme.AppString

@Immutable
internal data class OnboardingStrings(
    val lastPagePracticeNow: String,
    val lastPageQuit: String,
    val welcomeTitle: String,
    val welcomeCaption: String,
    val drawTitle: String,
    val drawCaption: String,
    val progressTitle: String,
    val progressCaption: String,
    val progressLevel: (Int) -> String,
) : AppString
