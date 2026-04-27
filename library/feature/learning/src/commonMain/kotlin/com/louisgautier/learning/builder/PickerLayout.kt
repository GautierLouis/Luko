package com.louisgautier.learning.builder

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.components.AdaptiveLayout
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import com.louisgautier.designsystem.token.dimens.Spacing

@Composable
internal fun PickerLayout(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {

    Box(
        modifier = modifier
            .wrapContentHeight()
            .background(
                color = Theme.materialColors.surfaceContainer,
                shape = ShapeDefaults.card()
            )
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = Theme.materialColors.outline
                ),
                shape = ShapeDefaults.card()
            ),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(Padding.large),
            verticalArrangement = Spacing.large,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = label,
                color = Theme.materialColors.onBackground,
                fontWeight = FontWeight.Medium,
            )
            AdaptiveLayout {
                content()
            }
        }
    }
}