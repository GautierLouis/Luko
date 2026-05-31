package xyz.luko.learning.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.BorderStrokeDefaults
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.ShapeDefaults
import xyz.luko.designsystem.token.dimens.Spacing
import xyz.luko.domain.model.Stroke
import xyz.luko.learning.drawing.DrawableArea
import xyz.luko.learning.drawing.DrawableAreaDefault

enum class Type {
    USER, REFERENCE
}

@Composable
internal fun RowScope.DrawableWrapper(
    data: List<Stroke>,
    type: Type,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(
                color = Theme.materialColors.surfaceContainer,
                shape = ShapeDefaults.card()
            )
            .padding(
                horizontal = Padding.large,
                vertical = Padding.medium
            ),
        verticalArrangement = Spacing.medium,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(if (type == Type.REFERENCE) "Reference" else "Your drawing")

        DrawableArea(
            enableDrawing = false,
            reference = emptyList(),
            userStroke = data,
            strokeWidth = DrawableAreaDefault.STROKE_WIDTH_MINI,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(
                    color = Theme.materialColors.surfaceVariant,
                    shape = ShapeDefaults.card()
                ).border(
                    border = BorderStrokeDefaults.medium(Theme.materialColors.outline),
                    shape = ShapeDefaults.tag(),
                ).padding(Padding.medium).clipToBounds()
        )
    }
}
