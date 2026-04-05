package com.louisgautier.designsystem.token.color

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import com.louisgautier.designsystem.token.color.palette.MaterialPalette

internal fun materialColorsNight(): ColorScheme = darkColorScheme(
    primary = MaterialPalette.Green100,
    onPrimary = MaterialPalette.Green980,
    primaryContainer = MaterialPalette.Green800,
    onPrimaryContainer = MaterialPalette.Green50,
    secondary = MaterialPalette.Sage300,
    onSecondary = MaterialPalette.Sage950,
    secondaryContainer = MaterialPalette.Sage800,
    onSecondaryContainer = MaterialPalette.Sage100,
    tertiary = MaterialPalette.Teal300,
    onTertiary = MaterialPalette.Teal970,
    tertiaryContainer = MaterialPalette.Teal800,
    onTertiaryContainer = MaterialPalette.Teal100,
    error = MaterialPalette.Red300,
    onError = MaterialPalette.Red970,
    errorContainer = MaterialPalette.Red800,
    onErrorContainer = MaterialPalette.Red100,
    background = MaterialPalette.Neutral960,
    onBackground = MaterialPalette.Neutral300,
    surface = MaterialPalette.Neutral960,
    onSurface = MaterialPalette.Neutral300,
    surfaceVariant = MaterialPalette.Neutral800,
    onSurfaceVariant = MaterialPalette.NeutralVariant300,
    outline = MaterialPalette.Neutral700,
    outlineVariant = MaterialPalette.Neutral800,
    scrim = MaterialPalette.NeutralBlack,
    inverseSurface = MaterialPalette.Neutral300,
    inverseOnSurface = MaterialPalette.Neutral900,
    inversePrimary = MaterialPalette.Green500,
    surfaceDim = MaterialPalette.Neutral960,
    surfaceBright = MaterialPalette.Neutral850,
    surfaceContainerLowest = MaterialPalette.Neutral970,
    surfaceContainerLow = MaterialPalette.Neutral940,
    surfaceContainer = MaterialPalette.Neutral930,
    surfaceContainerHigh = MaterialPalette.Neutral910,
    surfaceContainerHighest = MaterialPalette.Neutral880,
)