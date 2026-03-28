package com.louisgautier.designsystem

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.louisgautier.designsystem.ai.Green50
import com.louisgautier.designsystem.components.action.ActionContainer

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppTitle(
    title: String,
    modifier: Modifier = Modifier,
    leftIcons: @Composable RowScope.() -> Unit = {},
    rightIcons: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(title) },
        navigationIcon = {
            ActionContainer { leftIcons() }
        },
        actions = {
            ActionContainer { rightIcons() }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Green50,
            scrolledContainerColor = Color.Unspecified,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        )
    )
}

