package com.louisgautier.learning.builder

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisgautier.designsystem.ai.AppBadge
import com.louisgautier.designsystem.ai.Gray900
import com.louisgautier.designsystem.ai.LevelIconSelectable
import com.louisgautier.designsystem.components.attrs.DifficultyLevel
import com.louisgautier.designsystem.components.attrs.DifficultyLevel.Companion.caption
import com.louisgautier.designsystem.components.attrs.DifficultyLevel.Companion.label
import com.louisgautier.designsystem.components.attrs.DifficultyLevel.Companion.title
import com.louisgautier.designsystem.components.attrs.HSKLevel
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.color.v2.LevelColors
import com.louisgautier.designsystem.token.dimens.BorderStrokeDefaults
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import com.louisgautier.designsystem.token.dimens.Spacing
import com.louisgautier.designsystem.token.typo.FontWeight
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter


@Composable
internal fun LevelCardTitle(
    label: String,
    color: Color
) {
    Text(
        text = label,
        fontWeight = FontWeight.bold,
        style = Theme.typography.bodyLarge,
        color = color,
    )
}

@Composable
internal fun LevelCardTileWithBadge(
    difficulty: DifficultyLevel,
    colors: LevelColors
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LevelCardTitle(
            label = difficulty.title(),
            color = Theme.materialColors.onSurface
        )
        AppBadge(
            label = difficulty.label(),
            containerColor = colors.primary,
            contentColor = colors.onPrimary
        )
    }
}


@Composable
internal fun LevelCard(
    title: @Composable () -> Unit,
    caption: String,
    icon: ImageVector,
    color: LevelColors,
    modifier: Modifier = Modifier.Companion,
    selected: Boolean = false,
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
            .background(
                color = cardContainerColor,
                shape = ShapeDefaults.card()
            )
            .border(
                border = BorderStrokeDefaults.medium(borderColor),
                shape = ShapeDefaults.card()
            )
            .clickable(
                enabled = true,
                onClick = onClick,
                role = Role.Companion.Checkbox
            )
    ) {
        Row(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .padding(Padding.large),
            horizontalArrangement = Spacing.medium,
            verticalAlignment = Alignment.Companion.Top,
        ) {
            LevelIconSelectable(
                containerColor = iconContainerColor,
                contentColor = iconTint,
                icon = icon
            )
            Column(
                modifier = Modifier.Companion.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                title()
                Text(
                    text = caption,
                    style = Theme.typography.bodySmall,
                    color = textColor,
                    modifier = Modifier.Companion.padding(end = 30.dp)
                )
            }
        }
    }
}

@Composable
@Preview
private fun PreviewLevelCard(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        }
    }
}