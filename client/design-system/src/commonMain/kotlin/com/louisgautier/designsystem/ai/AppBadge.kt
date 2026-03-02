package com.louisgautier.designsystem.ai

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppBadge(
    modifier: Modifier = Modifier,
    label: String,
    containerColor: Color = Color.Unspecified,
    contentColor: Color = Color.Unspecified,
) {

    Surface(
        color = containerColor,
        shape = RoundedCornerShape(6.dp),
        modifier = modifier.padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            color = contentColor,
            style = SmallTextStyle,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AppBadgePreview() {
    MaterialTheme {
        AppBadge(
            label = "New",
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )

    }
}