@file:OptIn(ExperimentalMaterial3Api::class)

package com.louisgautier.dictionary

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisgautier.designsystem.TransformStroke
import com.louisgautier.domain.model.DictionaryWithGraphic
import com.louisgautier.domain.model.Graphic
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.previewDictionaryWithGraphic
import com.louisgautier.domain.previewSession
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun ModalCharacterDetails(
    character: DictionaryWithGraphic,
    modifier: Modifier = Modifier,
    lastSeenSession: List<Session> = emptyList(),
    onDismiss: () -> Unit = {},
    onPractice: () -> Unit = {},
) {

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        content = {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = character
                        .dictionary
                        .pinyin
                        .firstOrNull()
                        .orEmpty(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )

                AnimatedGraphic(character.graphics)

                Spacer(Modifier.height(32.dp))

//                AppButton(
//                    onClick = onPractice,
//                    modifier = Modifier.height(56.dp)
//                ) {
//                    Text("Practice")
//                }

                if (lastSeenSession.isNotEmpty()) {

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "Last Seen in:",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )

                    lastSeenSession.forEach {
                        //SessionCard(session = it)
                    }
                }
            }
        }
    )
}

@Composable
fun AnimatedGraphic(
    graphic: Graphic,
    modifier: Modifier = Modifier,
) {

    var canvasSize by remember { mutableStateOf(IntSize(0, 0)) }

    val strokes = TransformStroke.transformPath(graphic.strokes, canvasSize)
    val medians = graphic.medians.map { s -> s.points.map { p -> Offset(p.x, p.y) } }

    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        delay(500)
        animate(
            initialValue = 0f,
            targetValue = strokes.size.toFloat(),
            animationSpec = tween(3000, easing = LinearEasing)
        ) { value, _ -> progress = value }
    }

    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .width(150.dp)
                .aspectRatio(1f)
                .onGloballyPositioned { coordinates -> canvasSize = coordinates.size }
        ) {

            strokes.forEachIndexed { index, path ->

                val strokeProgress = (progress - index).coerceIn(0f, 1f)

                val currentMedian = TransformStroke.transformOffset(medians[index], canvasSize)
                if (strokeProgress > 0f) {
                    clipPath(path) {

                        val medianPath = Path()
                        val totalPoints = currentMedian.size
                        val currentIndex = (strokeProgress * (totalPoints - 1)).toInt()
                        val fraction = (strokeProgress * (totalPoints - 1)) - currentIndex

                        medianPath.moveTo(
                            currentMedian[0].x,
                            currentMedian[0].y
                        )

                        for (i in 1..currentIndex) {
                            medianPath.lineTo(
                                currentMedian[i].x,
                                currentMedian[i].y
                            )
                        }

                        if (currentIndex < totalPoints - 1) {
                            val current = currentMedian[currentIndex]
                            val next = currentMedian[currentIndex + 1]
                            medianPath.lineTo(
                                current.x + (next.x - current.x) * fraction,
                                current.y + (next.y - current.y) * fraction
                            )
                        }

                        drawPath(
                            path = medianPath,
                            color = Color.Black,
                            style = Stroke(width = 100f, cap = StrokeCap.Round)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ModalCharacterDetailsPreview() {
    ModalCharacterDetails(
        character = previewDictionaryWithGraphic,
        lastSeenSession = listOf(previewSession, previewSession)
    )
}
