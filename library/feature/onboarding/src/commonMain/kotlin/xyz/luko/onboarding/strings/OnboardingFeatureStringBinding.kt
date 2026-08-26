package xyz.luko.onboarding.strings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import xyz.luko.ui.designsystem.theme.FeatureStringBinding
import xyz.luko.ui.designsystem.theme.StringProvider
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.designsystem.token.string.StringsLocale

val onboardingStringProvider: FeatureStringBinding<*> = OnboardingFeatureStringBindingImpl

internal object OnboardingFeatureStringBindingImpl : FeatureStringBinding<OnboardingStrings> {
    override val local
        get() = LocalOnboardingString
    override val provider = object : StringProvider<OnboardingStrings> {
        override fun get(locale: StringsLocale) = when (locale) {
            StringsLocale.EN -> getEN()
            StringsLocale.FR -> getFR()
        }
    }
}

private fun getFR(): OnboardingStrings = OnboardingStrings(
    lastPagePracticeNow = "Apprends maintenant",
    lastPageQuit = "Quitter",
    welcomeTitle = "Bienvenue sur\nLuko",
    welcomeCaption = "Apprends à lire et à écrire des caractères chinois, un tracé à la fois.",
    drawTitle = "Apprends par dessin",
    drawCaption = "Trace et écrivez des caractères chinois réels avec votre doigt",
    progressTitle = "Un peu chaque jour",
    progressCaption = "Characters level up as you practice from your first glance to second nature. Come back daily to keep your streak alive.",
    progressLevel = { "Niveau $it" }
)

private fun getEN(): OnboardingStrings = OnboardingStrings(
    lastPagePracticeNow = "Practice now",
    lastPageQuit = "Quit",
    welcomeTitle = "Welcome to\nLuko",
    welcomeCaption = "Learn to read and write Chinese characters, one stroke at a time.",
    drawTitle = "Learn by drawing",
    drawCaption = "Trace and write real Chinese characters with your finger",
    progressTitle = "A little every day",
    progressCaption = "Characters level up as you practice from your first glance to second nature. Come back daily to keep your streak alive.",
    progressLevel = { "Level $it" }
)

private val LocalOnboardingString = staticCompositionLocalOf { getEN() }

internal val Theme.onboardingString: OnboardingStrings
    @Composable
    get() = LocalOnboardingString.current

