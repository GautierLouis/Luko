package com.louisgautier.composeApp.session

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisgautier.apicontracts.dto.Graphic
import com.louisgautier.apicontracts.dto.Point
import com.louisgautier.apicontracts.dto.Stroke
import com.louisgautier.composeApp.design.ai.Gray400
import com.louisgautier.composeApp.design.ai.Green700
import com.louisgautier.composeApp.design.modifier.dashedBorder
import com.louisgautier.composeApp.design.previewGraphic
import com.louisgautier.composeApp.drawing.DrawableArea
import com.louisgautier.composeApp.drawing.analyzeUserDrawing
import com.louisgautier.domain.model.Difficulty
import com.louisgautier.domain.model.Response
import kotlinx.coroutines.delay
import learn_chinese.client.composeapp.generated.resources.Res
import learn_chinese.client.composeapp.generated.resources.ic_reset
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
fun GraphicPager(
    difficulty: Difficulty,
    graphic: Graphic,
    modifier: Modifier = Modifier,
    onComplete: (Response) -> Unit = {}
) {
    var isDrawing by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .dashedBorder(
                width = 2.dp,
                color = Green700,
                shape = RoundedCornerShape(11.dp),
                on = 10.dp,
                off = 10.dp,
            ),
    ) {
        DrawableArea(
            medians = graphic.medians,
            drawReference = difficulty != Difficulty.HARD,
            drawHint = difficulty == Difficulty.EASY,
            onStartDrawing = { isDrawing = true },
            onComplete = { strokes, result ->
                onComplete(Response(graphic.code, result, strokes))
            }
        )
    }
}

@Composable
fun RewardToast(
    text: String,
    visible: Boolean,
    modifier: Modifier = Modifier,
    durationMillis: Int = 200,
    onFinished: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        delay(durationMillis.toLong())
        onFinished()
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        Box(
            modifier = modifier
                .background(
                    color = Green700,
                    shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp)
                )
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}


@Composable
fun ResetButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(34.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Gray400,
            contentColor = Color.White
        ),
        shape = ShapeDefaults.button()
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_reset),
            contentDescription = "reset drawing",
            modifier = Modifier
                .padding(8.dp)
                .graphicsLayer { scaleX = -1f }
        )
    }
}

class DifficultyPreviewParameter() : PreviewParameterProvider<Difficulty> {
    override val values: Sequence<Difficulty>
        get() = Difficulty.entries.asSequence()
}

@Preview
@Composable
fun GraphicPagerPreview(
    @PreviewParameter(DifficultyPreviewParameter::class) difficulty: Difficulty
) {
    GraphicPager(difficulty, previewGraphic)
}