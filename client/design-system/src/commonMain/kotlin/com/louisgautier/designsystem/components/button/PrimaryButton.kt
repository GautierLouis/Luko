package com.louisgautier.designsystem.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.token.dimens.BorderStrokeDefaults
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import com.louisgautier.designsystem.ai.Teal600
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = Teal600,
    contentColor: Color = Color.White,
    border: BorderStroke = BorderStrokeDefaults.zero(),
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    Button(
        shape = ShapeDefaults.button(),
        border = border,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        enabled = enabled,
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        content = { content() }
    )
}


@Composable
@Preview
fun PrimaryButtonPreview() {
    AppButton {
        Text("Hello")
    }
}