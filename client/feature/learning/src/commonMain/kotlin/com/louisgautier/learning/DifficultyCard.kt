package com.louisgautier.learning

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import com.louisgautier.designsystem.components.attrs.DifficultyLevel
import com.louisgautier.designsystem.components.attrs.DifficultyLevel.Companion.caption
import com.louisgautier.designsystem.components.attrs.DifficultyLevel.Companion.colorFamily
import com.louisgautier.designsystem.components.attrs.DifficultyLevel.Companion.icon
import com.louisgautier.designsystem.token.typo.FontWeight
import com.louisgautier.domain.model.Difficulty
import com.louisgautier.learning.builder.LevelCard
import com.louisgautier.learning.builder.LevelCardTileWithBadge
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun DifficultyCard(
    difficulty: DifficultyLevel,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {

    LevelCard(
        title = {
            LevelCardTileWithBadge(
                difficulty = difficulty,
                colors = difficulty.colorFamily()
            )
        },
        caption = difficulty.caption(),
        icon = difficulty.icon(),
        color = difficulty.colorFamily(),
        modifier = modifier,
        selected = selected,
        onClick = onClick,
    )
}


@Composable
@Preview
private fun PreviewDifficultyCard() {
    MaterialTheme {
        Column {
            DifficultyLevel.entries.forEach {
                DifficultyCard(difficulty = it)
                DifficultyCard(difficulty = it, selected = true)
            }
        }
    }
}