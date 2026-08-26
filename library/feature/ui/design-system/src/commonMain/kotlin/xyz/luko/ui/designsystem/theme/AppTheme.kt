package xyz.luko.ui.designsystem.theme

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.intl.Locale
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.token.color.AppLevelColorsDay
import xyz.luko.ui.designsystem.token.color.materialColorsDay
import xyz.luko.ui.designsystem.token.string.AppStringBinding
import xyz.luko.ui.designsystem.token.string.StringsLocale
import xyz.luko.ui.designsystem.token.typo.openHuninn

@Composable
fun AppTheme(
    themeMode: ThemeMode? = null,
    forcedLocale: StringsLocale? = null,
    featureStrings: List<FeatureStringBinding<*>> = emptyList(),
    content: @Composable () -> Unit,
) {
    val isSystemDark = isSystemInDarkTheme()
    val theme = themeMode ?: if (isSystemDark) ThemeMode.Night else ThemeMode.Day

    val locale = forcedLocale ?: Locale.current.toStringsLocale()
    val strings = AppStringBinding
    val featureValues = remember(locale, featureStrings) {
        featureStrings.map { it.local provides it.provider.get(locale) }
    }

    val materialColors = remember(theme) { theme.toMaterialColors() }
    val appLevelColors = remember(theme) { theme.toLevelColors() }

    val typography = openHuninn()

    SharedTransitionLayout {
        CompositionLocalProvider(
            *(featureValues.toTypedArray()),
            strings.local provides strings.provider.get(locale),
            LocalMaterialColors provides materialColors,
            LocalAppLevelColors provides appLevelColors,
            LocalTypography provides typography,
            LocalSharedTransitionScope provides this@SharedTransitionLayout,
        ) {
            content()
        }
    }
}

internal val LocalMaterialColors = staticCompositionLocalOf { materialColorsDay() }
internal val LocalAppLevelColors = staticCompositionLocalOf { AppLevelColorsDay }
internal val LocalTypography = staticCompositionLocalOf<Typography> {
    error("No Typography provided")
}
internal val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope> {
    error("No SharedTransitionScope provided")
}

val LocalAnimatedContentScope = compositionLocalOf<AnimatedContentScope> {
    error("No AnimatedContentScope provided")
}
