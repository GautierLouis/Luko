package xyz.luko.app.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import xyz.luko.app.main.MenuDefault.FloatingActionSize
import xyz.luko.app.main.MenuDefault.IconSize
import xyz.luko.ui.designsystem.onboarding.OnboardingKey
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.designsystem.token.dimens.BorderStrokeDefaults
import xyz.luko.ui.designsystem.token.dimens.Padding
import xyz.luko.ui.designsystem.token.dimens.ShapeDefaults
import xyz.luko.ui.designsystem.token.dimens.Spacing
import xyz.luko.ui.onboarding.registerTooltip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Menu(
    modifier: Modifier = Modifier,
    leadingMenuItems: ImmutableList<MenuItem> = persistentListOf(),
    trailingMenuItems: ImmutableList<MenuItem> = persistentListOf(),
    selectedItem: MenuItem = MenuItem.Home,
    orientation: Orientation = Orientation.Horizontal,
    onItemClick: (MenuItem) -> Unit = {},
    onMainItemClick: () -> Unit = {}
) {

    val spacer: @Composable () -> Unit = if (orientation == Orientation.Horizontal) {
        { Spacer(Modifier.width(FloatingActionSize)) }
    } else {
        { Spacer(Modifier.height(FloatingActionSize)) }
    }

    val containerModifier = Modifier
        .border(
            BorderStrokeDefaults.minimum(Theme.materialColors.outlineVariant),
            ShapeDefaults.roundButton()
        )
        .background(
            color = Theme.materialColors.secondaryContainer,
            shape = ShapeDefaults.roundButton()
        )

    val container: @Composable (@Composable () -> Unit) -> Unit =
        if (orientation == Orientation.Horizontal) {
            {
                Row(
                    modifier = containerModifier.padding(vertical = Padding.small),
                    horizontalArrangement = Spacing.large,
                    verticalAlignment = Alignment.CenterVertically,
                ) { it() }
            }
        } else {
            {
                Column(
                    modifier = containerModifier.padding(horizontal = Padding.small),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Spacing.large
                ) { it() }
            }
        }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        container {
            leadingMenuItems.forEach { item ->
                MenuItem(
                    item,
                    selected = selectedItem == item,
                    onClick = { onItemClick(item) }
                )
            }
            spacer()
            trailingMenuItems.forEach { item ->
                MenuItem(
                    item,
                    selected = selectedItem == item,
                    onClick = { onItemClick(item) }
                )
            }
        }

        MenuMainItem(
            modifier = Modifier.registerTooltip(
                key = OnboardingKey.HOME_MENU,
                anchorPosition = if (orientation == Orientation.Horizontal) TooltipAnchorPosition.Above else TooltipAnchorPosition.Left
            ),
            onClick = { onMainItemClick() }
        )
    }
}

@Composable
private fun MenuItem(
    item: MenuItem,
    selected: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title(),
            modifier = Modifier.size(IconSize),
            tint = if (selected) Theme.materialColors.onSecondaryContainer else Theme.materialColors.secondary
        )
    }
}

@Composable
private fun MenuMainItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        modifier = modifier.size(FloatingActionSize),
        shape = CircleShape,
        containerColor = Theme.materialColors.primary,
        onClick = onClick
    ) {
        Icon(
            imageVector = MenuItem.Session.icon,
            contentDescription = MenuItem.Session.title(),
            tint = Color.White,
            modifier = Modifier.size(IconSize)
        )
    }
}

@Preview
@Composable
private fun PreviewMenu(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        Column {
            Menu(
                leadingMenuItems = persistentListOf(
                    MenuItem.Home,
                ),
                trailingMenuItems = persistentListOf(
                    MenuItem.Dictionary
                )
            )
            Menu(
                orientation = Orientation.Vertical,
                leadingMenuItems = persistentListOf(
                    MenuItem.Home,
                ),
                trailingMenuItems = persistentListOf(
                    MenuItem.Dictionary
                )
            )
        }
    }
}
