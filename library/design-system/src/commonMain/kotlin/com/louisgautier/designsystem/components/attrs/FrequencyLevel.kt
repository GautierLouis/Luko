package com.louisgautier.designsystem.components.attrs

import androidx.compose.runtime.Composable
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedBolt
import com.louisgautier.designsystem.icon.RoundedStar
import com.louisgautier.designsystem.icon.RoundedTrophy
import com.louisgautier.designsystem.theme.Theme

enum class FrequencyLevel {
    COMMON,
    FREQUENT,
    STANDARD,
    ;

    companion object Companion {
        @Composable
        fun FrequencyLevel.label() =
            when (this) {
                COMMON -> Theme.strings.common
                FREQUENT -> Theme.strings.frequent
                STANDARD -> Theme.strings.standard
            }

        @Composable
        fun FrequencyLevel.caption() =
            when (this) {
                COMMON -> Theme.strings.commonCaption
                FREQUENT -> Theme.strings.frequentCaption
                STANDARD -> Theme.strings.standardCaption
            }

        @Composable
        fun FrequencyLevel.icon() =
            when (this) {
                COMMON -> AppIcon.RoundedStar
                FREQUENT -> AppIcon.RoundedBolt
                STANDARD -> AppIcon.RoundedTrophy
            }

        @Composable
        fun FrequencyLevel.colorFamily() =
            when (this) {
                COMMON -> Theme.appLevelColors.common
                FREQUENT -> Theme.appLevelColors.frequent
                STANDARD -> Theme.appLevelColors.standard
            }
    }
}
