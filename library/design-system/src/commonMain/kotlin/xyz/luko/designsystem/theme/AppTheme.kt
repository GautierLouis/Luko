package xyz.luko.designsystem.theme

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SharedTransitionDefaults
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.intl.Locale
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.token.color.AppLevelColorsDay
import xyz.luko.designsystem.token.color.materialColorsDay
import xyz.luko.designsystem.token.string.StringsLocale
import xyz.luko.designsystem.token.string.provideStringsEN
import xyz.luko.designsystem.token.typo.AppTypography

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

    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalMaterialColors provides materialColors,
            LocalAppLevelColors provides appLevelColors,
            LocalAppStrings provides strings,
            LocalTypography provides AppTypography,
            LocalSharedTransitionScope provides this@SharedTransitionLayout
        ) {
            content()
        }
    }
}

internal val LocalMaterialColors = staticCompositionLocalOf { materialColorsDay() }
internal val LocalAppLevelColors = staticCompositionLocalOf { AppLevelColorsDay }
internal val LocalAppStrings = staticCompositionLocalOf { provideStringsEN() }
internal val LocalTypography = staticCompositionLocalOf { AppTypography }
internal val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope> {
    error("No SharedTransitionScope provided")
}

val LocalAnimatedContentScope = compositionLocalOf<AnimatedContentScope> {
    error("No AnimatedContentScope provided")
}
