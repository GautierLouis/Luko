package xyz.luko.app.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import xyz.luko.app.main.MenuDefault.FloatingActionSize
import xyz.luko.app.main.MenuDefault.IconSize
import xyz.luko.ui.core.adaptive.AdaptiveContainer
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
    menuItems: ImmutableList<MenuItem> = persistentListOf(),
    selectedItem: MenuItem = MenuItem.Home,
    orientation: Orientation = Orientation.Horizontal,
    onItemClick: (MenuItem) -> Unit = {},
    onMainItemClick: () -> Unit = {}
) {

    val leading = remember(menuItems) { menuItems.take(menuItems.size / 2) }
    val trailing = remember(menuItems) { menuItems.drop(menuItems.size / 2) }

    val isHorizontal = orientation == Orientation.Horizontal

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AdaptiveContainer(
            useRow = isHorizontal,
            modifier = Modifier
                .border(
                    BorderStrokeDefaults.minimum(Theme.materialColors.outlineVariant),
                    ShapeDefaults.roundButton()
                )
                .background(
                    color = Theme.materialColors.secondaryContainer,
                    shape = ShapeDefaults.roundButton()
                )
                .padding(Padding.small)
                .then(
                    if (isHorizontal) Modifier.padding(horizontal = Padding.small)
                    else Modifier.padding(vertical = Padding.small)
                ),
            horizontalArrangement = Spacing.large,
            verticalArrangement = Spacing.large,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            leading.forEach { item ->
                MenuItem(
                    item = item,
                    selected = selectedItem == item,
                    onClick = { onItemClick(item) }
                )
            }

            if (orientation == Orientation.Horizontal) {
                Spacer(Modifier.width(FloatingActionSize))
            } else {
                Spacer(Modifier.height(FloatingActionSize))
            }

            trailing.forEach { item ->
                MenuItem(
                    item = item,
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
        onClick = onClick,
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title(),
            modifier = Modifier.size(IconSize),
            tint = if (selected) Theme.materialColors.tertiary else Theme.materialColors.secondary
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
            tint = Theme.materialColors.onPrimary,
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
                menuItems = persistentListOf(
                    MenuItem.Home,
                    MenuItem.Dictionary
                )
            )
            Menu(
                orientation = Orientation.Vertical,
                menuItems = persistentListOf(
                    MenuItem.Home,
                    MenuItem.Dictionary
                )
            )
        }
    }
}
