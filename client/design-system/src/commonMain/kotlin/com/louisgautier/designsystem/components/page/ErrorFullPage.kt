package com.louisgautier.designsystem.components.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.AppTitle
import com.louisgautier.designsystem.ai.Green50
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ErrorPage(
    modifier: Modifier = Modifier,
    action: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        containerColor = Green50,
        topBar = { AppTitle("") }
    ) { paddingValues -> ErrorPageContent(Modifier.padding(paddingValues)) { action() } }
}

@Composable
fun ErrorPageContent(
    modifier: Modifier = Modifier,
    action: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Failed to load")
        Spacer(Modifier.height(8.dp))
        Button(onClick = action) { Text("Retry") }
    }
}

@Preview
@Composable
fun ErrorFullPagePreview() {
    ErrorPageContent { }
}