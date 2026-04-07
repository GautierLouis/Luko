package com.louisgautier.app.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun BottomBar(
    items: ImmutableList<BottomNavItem>,
    selectedItem: BottomNavItem,
    modifier: Modifier = Modifier,
    onClick: (BottomNavItem) -> Unit = {}
) {
    NavigationBar(
        modifier = modifier,
        containerColor = Theme.materialColors.surfaceContainer,
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem == item,
                onClick = { onClick(item) },
                icon = { Icon(item.icon, contentDescription = null) },
                label = {
                    Text(
                        text = item.title(),
                        style = Theme.typography.labelMedium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Theme.materialColors.primaryContainer,
                    selectedTextColor = Theme.materialColors.onSurface,
                    unselectedTextColor = Theme.materialColors.onSurface,
                    selectedIconColor = Theme.materialColors.onSurface,
                    unselectedIconColor = Theme.materialColors.onSurface,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBottomBar(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        Column {
            Spacer(Modifier.height(56.dp).background(Theme.materialColors.background))
            BottomBar(
                BottomNavItem.entries.toImmutableList(),
                BottomNavItem.Home
            )
        }
    }
}