package com.louisgautier.app.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.louisgautier.utils.Flavor

@Composable
internal fun FlavorComponent(
    flavor: Flavor,
) {

    val density = LocalDensity.current
    val height = with(density) { WindowInsets.statusBars.getTop(this).toDp() }

    Column(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .background(if (flavor == Flavor.DEV) Color.Red else Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = flavor.name.uppercase(),
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun PreviewFlavorComponent() {
    FlavorComponent(Flavor.DEV)
}