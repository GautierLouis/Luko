package com.louisgautier.learning.builder

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.components.LabelTag
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.color.model.LevelColors
import com.louisgautier.designsystem.token.dimens.BorderStrokeDefaults
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import com.louisgautier.designsystem.token.dimens.Spacing
import com.louisgautier.designsystem.token.typo.FontWeight

@Composable
internal fun LevelCard(
    title: String,
    caption: String,
    icon: ImageVector,
    color: LevelColors,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    tagLabel: String? = null,
    onClick: () -> Unit
) {

    val borderColor = if (selected) color.primary else Theme.materialColors.outline
    val iconTint = if (selected) color.primary else Theme.materialColors.outline
    val cardContainerColor = if (selected) color.subtle else Theme.materialColors.surfaceContainer
    val iconContainerColor =
        if (selected) color.subtle else Theme.materialColors.outline.copy(alpha = .2f)
    val textColor = Theme.materialColors.onSurface

    Box(
        modifier = modifier
            .clip(ShapeDefaults.card())
            .background(
                color = cardContainerColor,
                shape = ShapeDefaults.card()
            )
            .border(
                border = BorderStrokeDefaults.medium(borderColor),
                shape = ShapeDefaults.card()
            )
            .clickable(
                onClick = onClick,
                onClickLabel = tagLabel ?: title,
                role = Role.Checkbox,
                indication = ripple(
                    color = color.primary,
                ),
                interactionSource = remember { MutableInteractionSource() },
                enabled = true
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Padding.large),
            horizontalArrangement = Spacing.medium,
            verticalAlignment = Alignment.Top,
        ) {
            LevelIconSelectable(
                containerColor = iconContainerColor,
                contentColor = iconTint,
                icon = icon
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Spacing.medium
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.bold,
                        style = Theme.typography.bodyLarge,
                        color = Theme.materialColors.onSurface,
                        modifier = Modifier.weight(1f),
                    )
                    tagLabel?.let {
                        LabelTag(
                            label = tagLabel,
                            containerColor = color.primary,
                            contentColor = color.onPrimary
                        )
                    }
                }
                Text(
                    text = caption,
                    style = Theme.typography.bodySmall,
                    color = textColor,
                )
            }
        }
    }
}