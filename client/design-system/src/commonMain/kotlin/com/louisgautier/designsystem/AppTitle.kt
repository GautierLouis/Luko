package com.louisgautier.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.louisgautier.designsystem.ai.Green50
import com.louisgautier.utils.AppNavigation

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppTitle(
    title: String,
    navigationIcon: @Composable () -> Unit = {
        TopBackButton()
    },
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(title) },
        navigationIcon = navigationIcon,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Green50,
            scrolledContainerColor = Color.Unspecified,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        )
    )
}

@Composable
fun TopBackButton(
    modifier: Modifier = Modifier,
) {
    IconButton(modifier = modifier, onClick = { AppNavigation.navigateUp() }) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = null
        )
    }
}