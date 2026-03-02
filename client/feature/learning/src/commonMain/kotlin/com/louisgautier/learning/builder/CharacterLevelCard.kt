package com.louisgautier.learning.builder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisgautier.designsystem.ai.AppCard
import com.louisgautier.designsystem.ai.CardIcon
import com.louisgautier.designsystem.ai.Gray200
import com.louisgautier.designsystem.ai.Gray400
import com.louisgautier.designsystem.ai.Gray900
import com.louisgautier.designsystem.ai.LightColors
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedBolt
import com.louisgautier.designsystem.icon.RoundedCrown
import com.louisgautier.designsystem.icon.RoundedSettings
import com.louisgautier.designsystem.icon.RoundedStar
import com.louisgautier.designsystem.icon.RoundedTrophy
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.token.typo.FontWeight
import com.louisgautier.domain.model.CharacterFrequencyLevel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharacterLevelCard(
    level: CharacterFrequencyLevel,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {

    val borderColor = when (level) {
        CharacterFrequencyLevel.COMMON -> LightColors.commonBorder
        CharacterFrequencyLevel.FREQUENT -> LightColors.frequentBorder
        CharacterFrequencyLevel.STANDARD -> LightColors.standardBorder
        CharacterFrequencyLevel.EXTENDED -> LightColors.extendedBorder
        else -> AppTheme.colors.grayFamily.solid
    }

    val backgroundColor = when (level) {
        CharacterFrequencyLevel.COMMON -> LightColors.commonBg
        CharacterFrequencyLevel.FREQUENT -> LightColors.frequentBg
        CharacterFrequencyLevel.STANDARD -> LightColors.standardBg
        CharacterFrequencyLevel.EXTENDED -> LightColors.extendedBg
        else -> AppTheme.colors.grayFamily.solid
    }

    val desc = when (level) {
        CharacterFrequencyLevel.COMMON ->
            "Most frequently used characters in everyday writing and communication. Includes the top 500 essential characters for basic comprehension."

        CharacterFrequencyLevel.FREQUENT ->
            "Widely used characters found across common texts, media, and education. Covers the next 1000 characters after the core common set."

        CharacterFrequencyLevel.STANDARD ->
            "Standard literacy range for reading most books, newspapers, and digital content. Represents about more than 1500 characters beyond common and frequent ones."

        CharacterFrequencyLevel.EXTENDED ->
            "Rare or specialized characters used in names, classical works, and technical domains. Adds +6000 less common characters for full language coverage."

        else -> "你是中国人吗？"
    }

    val icon = when (level) {
        CharacterFrequencyLevel.COMMON -> AppIcon.RoundedStar
        CharacterFrequencyLevel.FREQUENT -> AppIcon.RoundedBolt
        CharacterFrequencyLevel.STANDARD -> AppIcon.RoundedTrophy
        CharacterFrequencyLevel.EXTENDED -> AppIcon.RoundedCrown
        else -> AppIcon.RoundedSettings
    }

    AppCard(
        modifier = modifier,
        borderColor = if (selected) borderColor else Gray400,
        backgroundColor = if (selected) backgroundColor else LightColors.background,
        onClick = onClick,
        icon = {
            CardIcon(
                backgroundColor = if (selected) backgroundColor else Gray200,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier,
                    tint = if (selected) borderColor else Gray400
                )
            }
        },
        title = {
            Text(
                text = level.name.lowercase().capitalize(Locale.current),
                fontWeight = FontWeight.bold,
                fontSize = 18.sp,
                color = Gray900,
            )
        },
        label = {
            Text(
                text = desc,
                fontSize = 11.sp,
                color = Gray400,
                modifier = Modifier.padding(end = 30.dp)
            )
        }
    )
}

@Composable
@Preview
fun CardsPreview() {
    MaterialTheme {
        Column {
            CharacterLevelCard(level = CharacterFrequencyLevel.COMMON)
            CharacterLevelCard(level = CharacterFrequencyLevel.FREQUENT, selected = true)
        }

    }
}