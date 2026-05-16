package xyz.luko.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.Check
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.BorderStrokeDefaults
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.ShapeDefaults
import xyz.luko.designsystem.token.dimens.Spacing
import xyz.luko.domain.repository.SettingTheme

@Composable
internal fun SettingPicker(
    section: SettingSection,
    selected: SettingTheme,
    modifier: Modifier = Modifier,
    onClick: (SettingTheme) -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Spacing.medium,
    ) {
        Text(
            text =
                when (section) {
                    is SettingSection.Theme -> "THEME"
                },
            style = Theme.typography.titleMedium,
            color = Theme.materialColors.outline,
        )
        Column(
            modifier =
                Modifier
                    .background(
                        color = Theme.materialColors.surfaceContainer,
                        shape = ShapeDefaults.card(),
                    ).border(
                        border = BorderStrokeDefaults.minimum(Theme.materialColors.outline),
                        shape = ShapeDefaults.card(),
                    ),
        ) {
            section.items.forEachIndexed { index, item ->
                if (index > 0) {
                    HorizontalDivider(thickness = 1.dp, color = Theme.materialColors.outline)
                }

                val shape =
                    when (index) {
                        0 ->
                            RoundedCornerShape(
                                topStart = Padding.large,
                                topEnd = Padding.large,
                            )

                        section.items.lastIndex ->
                            RoundedCornerShape(
                                bottomStart = Padding.large,
                                bottomEnd = Padding.large,
                            )

                        else -> RoundedCornerShape(0.dp)
                    }

                val label =
                    when (item) {
                        SettingTheme.Default -> "Default"
                        SettingTheme.Night -> "Night"
                        SettingTheme.Day -> "Day"
                    }

                Item(
                    label = label,
                    selected = selected == item,
                    modifier =
                        Modifier
                            .clip(shape)
                            .clickable(
                                onClick = { onClick(item) },
                                onClickLabel = label,
                                role = Role.RadioButton,
                            ),
                )
            }
        }
    }
}

@Composable
private fun Item(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(Padding.large),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = Theme.typography.titleMedium,
            color = if (selected) Theme.materialColors.primary else Theme.materialColors.onSurface,
        )
        if (selected) {
            Icon(
                imageVector = AppIcon.Check,
                contentDescription = null,
                tint = Theme.materialColors.primary,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSettingPicker(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        SettingPicker(
            selected = SettingTheme.Default,
            section =
                SettingSection.Theme(
                    items =
                        persistentListOf(
                            SettingTheme.Default,
                            SettingTheme.Night,
                            SettingTheme.Day,
                        ),
                ),
        )
    }
}
