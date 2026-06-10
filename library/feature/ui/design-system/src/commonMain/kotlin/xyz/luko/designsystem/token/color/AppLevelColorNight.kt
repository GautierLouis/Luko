package xyz.luko.designsystem.token.color

import xyz.luko.designsystem.token.color.model.AppLevelColors
import xyz.luko.designsystem.token.color.model.LevelColors
import xyz.luko.designsystem.token.color.palette.AppPalette
import xyz.luko.designsystem.token.color.palette.MaterialPalette

internal val AppLevelColorNight =
    AppLevelColors(
        common =
            LevelColors(
                primary = AppPalette.amber300,
                onPrimary = AppPalette.amber990,
                subtle = AppPalette.amber950,
                onSubtle = AppPalette.amber200,
            ),
        frequent =
            LevelColors(
                primary = AppPalette.teal300,
                onPrimary = AppPalette.teal990,
                subtle = AppPalette.teal950,
                onSubtle = AppPalette.teal200,
            ),
        standard =
            LevelColors(
                primary = AppPalette.indigo300,
                onPrimary = AppPalette.indigo990,
                subtle = AppPalette.indigo950,
                onSubtle = AppPalette.indigo200,
            ),
        easy =
            LevelColors(
                primary = AppPalette.green400,
                onPrimary = AppPalette.green990,
                subtle = AppPalette.green950,
                onSubtle = AppPalette.green200,
            ),
        medium =
            LevelColors(
                primary = AppPalette.orange400,
                onPrimary = AppPalette.orange990,
                subtle = AppPalette.orange950,
                onSubtle = AppPalette.orange200,
            ),
        hard =
            LevelColors(
                primary = AppPalette.red400,
                onPrimary = AppPalette.red990,
                subtle = AppPalette.red950,
                onSubtle = AppPalette.red200,
            ),
        streak =
            LevelColors(
                primary = AppPalette.orange600,
                onPrimary = AppPalette.black,
                subtle = AppPalette.orange900,
                onSubtle = AppPalette.orange300,
            ),
        sessions =
            LevelColors(
                primary = AppPalette.teal600,
                onPrimary = AppPalette.white,
                subtle = AppPalette.teal900,
                onSubtle = AppPalette.teal300,
            ),
        totalScore =
            LevelColors(
                primary = AppPalette.purple600,
                onPrimary = AppPalette.white,
                subtle = AppPalette.purple900,
                onSubtle = AppPalette.purple300,
            ),
        appMetrics =
            LevelColors(
                primary = MaterialPalette.Sage300,
                onPrimary = MaterialPalette.Sage990,
                subtle = MaterialPalette.Sage800,
                onSubtle = MaterialPalette.Sage100,
            ),
    )
