package com.louisgautier.designsystem.components

import androidx.compose.runtime.Composable
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedBolt
import com.louisgautier.designsystem.icon.RoundedStar
import com.louisgautier.designsystem.icon.RoundedTrophy
import com.louisgautier.designsystem.theme.Theme

enum class CharacterFrequency {
    COMMON,
    FREQUENT,
    STANDARD;

    companion object {
        @Composable
        fun CharacterFrequency.label() = when (this) {
            COMMON -> "Common"
            FREQUENT -> "Frequent"
            STANDARD -> "Standard"
        }

        @Composable
        fun CharacterFrequency.caption() = when (this) {
            COMMON -> "Most frequently used characters in everyday writing and communication. Includes the top 500 essential characters for basic comprehension."
            FREQUENT -> "Widely used characters found across common texts, media, and education. Covers the next 1000 characters after the core common set."
            STANDARD -> "Standard literacy range for reading most books, newspapers, and digital content. Represents about more than 1500 characters beyond common and frequent ones."
        }

        @Composable
        fun CharacterFrequency.icon() = when (this) {
            COMMON -> AppIcon.RoundedStar
            FREQUENT -> AppIcon.RoundedBolt
            STANDARD -> AppIcon.RoundedTrophy
        }

        @Composable
        fun CharacterFrequency.colorFamily() = when (this) {
            COMMON -> Theme.colors.greenFamily
            FREQUENT -> Theme.colors.yellowFamily
            STANDARD -> Theme.colors.redFamily
        }
    }
}