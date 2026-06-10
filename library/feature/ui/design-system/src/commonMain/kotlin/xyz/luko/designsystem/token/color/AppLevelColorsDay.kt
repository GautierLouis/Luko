package xyz.luko.designsystem.token.color

import xyz.luko.designsystem.token.color.model.AppLevelColors
import xyz.luko.designsystem.token.color.model.LevelColors
import xyz.luko.designsystem.token.color.palette.AppPalette
import xyz.luko.designsystem.token.color.palette.MaterialPalette

internal val AppLevelColorsDay =
    AppLevelColors(
        common =
            LevelColors(
                primary = AppPalette.amber700,
                onPrimary = AppPalette.amberSurface,
                subtle = AppPalette.amber100,
                onSubtle = AppPalette.amber800,
            ),
        frequent =
            LevelColors(
                primary = AppPalette.teal700,
                onPrimary = AppPalette.tealSurface,
                subtle = AppPalette.teal100,
                onSubtle = AppPalette.teal900,
            ),
        standard =
            LevelColors(
                primary = AppPalette.indigo500,
                onPrimary = AppPalette.indigoSurface,
                subtle = AppPalette.indigo100,
                onSubtle = AppPalette.indigo900,
            ),
        easy =
            LevelColors(
                primary = AppPalette.green700,
                onPrimary = AppPalette.greenSurface,
                subtle = AppPalette.green100,
                onSubtle = AppPalette.green900,
            ),
        medium =
            LevelColors(
                primary = AppPalette.orange700,
                onPrimary = AppPalette.orangeSurface,
                subtle = AppPalette.orange100,
                onSubtle = AppPalette.orange900,
            ),
        hard =
            LevelColors(
                primary = AppPalette.red700,
                onPrimary = AppPalette.redSurface,
                subtle = AppPalette.red100,
                onSubtle = AppPalette.red900,
            ),
        streak =
            LevelColors(
                primary = AppPalette.orange600,
                onPrimary = AppPalette.white,
                subtle = AppPalette.orange100,
                onSubtle = AppPalette.orange700,
            ),
        sessions =
            LevelColors(
                primary = AppPalette.teal600,
                onPrimary = AppPalette.white,
                subtle = AppPalette.teal100,
                onSubtle = AppPalette.teal700,
            ),
        totalScore =
            LevelColors(
                primary = AppPalette.purple600,
                onPrimary = AppPalette.white,
                subtle = AppPalette.purple100,
                onSubtle = AppPalette.purple700,
            ),
        appMetrics =
            LevelColors(
                primary = MaterialPalette.Sage700,
                onPrimary = AppPalette.white,
                subtle = MaterialPalette.Sage100,
                onSubtle = MaterialPalette.Sage800,
            ),
    )
