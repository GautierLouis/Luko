package xyz.luko.learning.builder

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import xyz.luko.baseui.session.caption
import xyz.luko.baseui.session.colorFamily
import xyz.luko.baseui.session.label
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.ui.designsystem.components.attrs.FrequencyLevel
import xyz.luko.ui.designsystem.components.attrs.FrequencyLevel.Companion.colorFamily
import xyz.luko.ui.designsystem.components.attrs.FrequencyLevel.Companion.label
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.designsystem.token.color.model.LevelColors

@Composable
internal fun CountItem(
    state: SessionBuilderViewModel.UiState,
    onClick: (SessionBuilderScreenEvent) -> Unit = {}
) {

    BuilderBaseItem("Count".uppercase()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    border = BorderStroke(1.dp, Theme.materialColors.outline),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = { onClick(SessionBuilderScreenEvent.QuestionCountDecrease) },
                enabled = state.canDecrease
            ) {
                Text(text = "-")
            }

            AnimatedContent(
                targetState = state.questionCount,
                modifier = Modifier.weight(1f),
                transitionSpec = {
                    val incrementing = targetState.ordinal > initialState.ordinal
                    if (incrementing) {
                        // new value comes from the top, old value pushed down and out
                        (slideInVertically { height -> -height } + fadeIn())
                            .togetherWith(slideOutVertically { height -> height } + fadeOut())
                    } else {
                        // reverse: new value comes from the bottom, old value pushed up and out
                        (slideInVertically { height -> height } + fadeIn())
                            .togetherWith(slideOutVertically { height -> -height } + fadeOut())
                    }.using(SizeTransform(clip = true))
                },
                label = "question_count"
            ) { value ->
                Text(
                    text = value.value.toString(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            TextButton(
                onClick = { onClick(SessionBuilderScreenEvent.QuestionCountIncrease) },
                enabled = state.canIncrease
            ) {
                Text(text = "+")
            }
        }
    }
}

@Composable
internal fun DifficultyItem(
    state: SessionBuilderViewModel.UiState,
    onClick: (DifficultyLevel) -> Unit = {},
) {
    BuilderBaseItem(
        "Difficulty".uppercase(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DifficultyLevel.entries.forEach {
                    BaseSelectableItem(
                        label = it.label(),
                        colors = it.colorFamily(),
                        isSelected = it == state.difficulty,
                        modifier = Modifier.weight(1f),
                        onClick = { onClick(it) }
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Theme.materialColors.tertiaryContainer.copy(alpha = .4f),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = state.difficulty.caption()
                )
            }
        }
    }
}

@Composable
internal fun FrequencyItem(
    state: SessionBuilderViewModel.UiState,
    onClick: (FrequencyLevel) -> Unit = {},
) {
    BuilderBaseItem(
        "Frequency".uppercase(),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FrequencyLevel.entries.forEach {
                BaseSelectableItem(
                    label = it.label(),
                    colors = it.colorFamily(),
                    isSelected = it in state.levels,
                    modifier = Modifier.weight(1f),
                    onClick = { onClick(it) }
                )
            }
        }
    }
}

@Composable
private fun BuilderBaseItem(
    title: String,
    modifier: Modifier = Modifier.Companion,
    content: @Composable () -> Unit = {},
) {
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title.uppercase(),
            style = Theme.typography.labelMedium,
            color = Theme.materialColors.outline,
            fontWeight = FontWeight.SemiBold
        )
        content()
    }
}

@Composable
private fun RowScope.BaseSelectableItem(
    label: String,
    colors: LevelColors,
    isSelected: Boolean,
    modifier: Modifier = Modifier.Companion,
    onClick: () -> Unit = {},
) {
    val borderColor = if (isSelected) colors.primary else Theme.materialColors.outline
    val containerColor =
        if (isSelected) colors.subtle else Theme.materialColors.surfaceContainer
    val textColor = Theme.materialColors.onSurface

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .weight(1f)
            .background(
                color = containerColor,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .border(
                BorderStroke(1.dp, borderColor),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .clickable(
                onClick = onClick,
                onClickLabel = label,
                role = Role.Checkbox,
                indication = ripple(
                    color = colors.primary,
                ),
                interactionSource = remember { MutableInteractionSource() },
                enabled = true,
            )
            .padding(8.dp)
    ) {
        Text(
            text = label,
            color = textColor
        )
    }
}
