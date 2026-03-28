package com.louisgautier.composeApp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import com.louisgautier.utils.AppConfig
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.mp.KoinPlatform

@Composable
fun FlavorComponent(
    flavor: String,
) {

    val density = LocalDensity.current
    val height = with(density) { WindowInsets.statusBars.getTop(this).toDp() }

    Column(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .background(if (flavor == "dev") Color.Red else Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = flavor.uppercase(),
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun PreviewFlavorComponent() {
    FlavorComponent("Dev")
}