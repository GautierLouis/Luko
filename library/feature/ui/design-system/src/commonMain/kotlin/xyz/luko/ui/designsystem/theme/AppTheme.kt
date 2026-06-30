package xyz.luko.ui.designsystem.theme

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.intl.Locale
import xyz.luko.ui.designsystem.onboarding.TooltipData
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.token.color.AppLevelColorsDay
import xyz.luko.ui.designsystem.token.color.materialColorsDay
import xyz.luko.ui.designsystem.token.string.StringsLocale
import xyz.luko.ui.designsystem.token.string.provideStringsEN
import xyz.luko.ui.designsystem.token.typo.openHuninn

@Composable
fun AppTheme(
    themeMode: ThemeMode,
    forcedLocale: StringsLocale? = null,
    content: @Composable () -> Unit,
) {
    val locale = forcedLocale ?: Locale.current.toStringsLocale()
    val strings = remember(locale) { locale.toStrings() }
    val materialColors = remember(themeMode) { themeMode.toMaterialColors() }
    val appLevelColors = remember(themeMode) { themeMode.toLevelColors() }

    val typography = openHuninn()

    val onboardingState = remember { mutableStateOf<List<TooltipData>>(emptyList()) }

    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalMaterialColors provides materialColors,
            LocalAppLevelColors provides appLevelColors,
            LocalAppStrings provides strings,
            LocalTypography provides typography,
            LocalSharedTransitionScope provides this@SharedTransitionLayout,
            LocalOnboardingState provides onboardingState
        ) {
            content()
        }
    }
}

internal val LocalMaterialColors = staticCompositionLocalOf { materialColorsDay() }
internal val LocalAppLevelColors = staticCompositionLocalOf { AppLevelColorsDay }
internal val LocalAppStrings = staticCompositionLocalOf { provideStringsEN() }
internal val LocalTypography = staticCompositionLocalOf<Typography> {
    error("No Typography provided")
}
internal val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope> {
    error("No SharedTransitionScope provided")
}

val LocalAnimatedContentScope = compositionLocalOf<AnimatedContentScope> {
    error("No AnimatedContentScope provided")
}

val LocalOnboardingState = compositionLocalOf { mutableStateOf<List<TooltipData>>(emptyList()) }
