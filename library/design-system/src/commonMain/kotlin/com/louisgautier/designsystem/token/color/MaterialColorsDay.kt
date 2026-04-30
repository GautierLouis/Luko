package com.louisgautier.designsystem.token.color

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import com.louisgautier.designsystem.token.color.palette.MaterialPalette

internal fun materialColorsDay(): ColorScheme =
    lightColorScheme(
        primary = MaterialPalette.Green500,
        onPrimary = MaterialPalette.NeutralWhite,
        primaryContainer = MaterialPalette.Green50,
        onPrimaryContainer = MaterialPalette.Green800,
        secondary = MaterialPalette.Sage700,
        onSecondary = MaterialPalette.NeutralWhite,
        secondaryContainer = MaterialPalette.Sage100,
        onSecondaryContainer = MaterialPalette.Sage800,
        tertiary = MaterialPalette.Teal700,
        onTertiary = MaterialPalette.NeutralWhite,
        tertiaryContainer = MaterialPalette.Teal100,
        onTertiaryContainer = MaterialPalette.Teal800,
        error = MaterialPalette.Red700,
        onError = MaterialPalette.NeutralWhite,
        errorContainer = MaterialPalette.Red100,
        onErrorContainer = MaterialPalette.Red900,
        background = MaterialPalette.Neutral50,
        onBackground = MaterialPalette.Neutral940,
        surface = MaterialPalette.Neutral50,
        onSurface = MaterialPalette.Neutral940,
        surfaceVariant = MaterialPalette.NeutralVariant200,
        onSurfaceVariant = MaterialPalette.NeutralVariant980,
        outline = MaterialPalette.Neutral750,
        outlineVariant = MaterialPalette.NeutralVariant300,
        scrim = MaterialPalette.NeutralBlack,
        inverseSurface = MaterialPalette.Neutral900,
        inverseOnSurface = MaterialPalette.Neutral150,
        inversePrimary = MaterialPalette.Green100,
        surfaceDim = MaterialPalette.Neutral400,
        surfaceBright = MaterialPalette.Neutral50,
        surfaceContainerLowest = MaterialPalette.NeutralWhite,
        surfaceContainerLow = MaterialPalette.Neutral100,
        surfaceContainer = MaterialPalette.Neutral200,
        surfaceContainerHigh = MaterialPalette.Neutral250,
        surfaceContainerHighest = MaterialPalette.Neutral300,
    )
