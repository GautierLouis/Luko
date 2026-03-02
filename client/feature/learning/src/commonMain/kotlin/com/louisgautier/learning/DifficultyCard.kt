package com.louisgautier.learning

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisgautier.designsystem.ai.AppBadge
import com.louisgautier.designsystem.ai.AppCard
import com.louisgautier.designsystem.ai.CardIcon
import com.louisgautier.designsystem.ai.Gray200
import com.louisgautier.designsystem.ai.Gray400
import com.louisgautier.designsystem.ai.Gray900
import com.louisgautier.designsystem.ai.Green100
import com.louisgautier.designsystem.ai.Green50
import com.louisgautier.designsystem.ai.Green700
import com.louisgautier.designsystem.ai.LightColors
import com.louisgautier.designsystem.ai.Orange100
import com.louisgautier.designsystem.ai.Orange50
import com.louisgautier.designsystem.ai.Orange700
import com.louisgautier.designsystem.ai.Red100
import com.louisgautier.designsystem.ai.Red50
import com.louisgautier.designsystem.ai.Red700
import com.louisgautier.designsystem.token.typo.FontWeight
import com.louisgautier.domain.model.Difficulty
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DifficultyCard(
    difficulty: Difficulty,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {

    val containerColor = when (difficulty) {
        Difficulty.EASY -> Green100
        Difficulty.MEDIUM -> Orange100
        Difficulty.HARD -> Red100
    }
    val backgroundColor = when (difficulty) {
        Difficulty.EASY -> Green50
        Difficulty.MEDIUM -> Orange50
        Difficulty.HARD -> Red50
    }

    val contentColor = when (difficulty) {
        Difficulty.EASY -> Green700
        Difficulty.MEDIUM -> Orange700
        Difficulty.HARD -> Red700
    }

    val icon = when (difficulty) {
        Difficulty.EASY -> Icons.Filled.Lightbulb
        Difficulty.MEDIUM -> Icons.Filled.Visibility
        Difficulty.HARD -> Icons.Filled.VisibilityOff
    }

    val title = when (difficulty) {
        Difficulty.EASY -> "Full Helper Mode"
        Difficulty.MEDIUM -> "Pinyin + Character"
        Difficulty.HARD -> "Pinyin Only"
    }

    val label = when (difficulty) {
        Difficulty.EASY -> "With meaning hints"
        Difficulty.MEDIUM -> "See answer as reference"
        Difficulty.HARD -> "Most challenging - no hints"
    }
    AppCard(
        modifier = modifier,
        borderColor = if (selected) contentColor else Gray400,
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
                    tint = if (selected) contentColor else Gray400
                )
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.bold,
                    fontSize = 18.sp,
                    color = Gray900,
                )
                AppBadge(
                    label = difficulty.name.lowercase().capitalize(Locale.current),
                    containerColor = containerColor,
                    contentColor = contentColor
                )
            }
        },
        label = {
            Text(
                text = label,
                fontSize = 11.sp,
                color = Gray400,
                modifier = Modifier.padding(end = 30.dp)
            )
        }
    )
}


@Composable
@Preview
fun DifficultyCardPreview() {
    MaterialTheme {
        Column {
            DifficultyCard(difficulty = Difficulty.EASY) { }
            DifficultyCard(difficulty = Difficulty.EASY, selected = true) { }

            DifficultyCard(difficulty = Difficulty.MEDIUM) { }
            DifficultyCard(difficulty = Difficulty.MEDIUM, selected = true) { }

            DifficultyCard(difficulty = Difficulty.HARD) { }
            DifficultyCard(difficulty = Difficulty.HARD, selected = true) { }
        }
    }
}