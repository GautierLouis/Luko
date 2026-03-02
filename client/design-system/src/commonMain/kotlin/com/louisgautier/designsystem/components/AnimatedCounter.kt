package com.louisgautier.designsystem.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedCounter(
    value: Int,
    content: @Composable (Int) -> Unit,
) {

    fun <T> getTween() = tween<T>(280)

    val fadeIn = fadeIn(animationSpec = getTween())
    val fadeOut = fadeOut(animationSpec = getTween())

    fun slideIn(inc: Boolean) =
        slideInVertically(animationSpec = getTween()) { fullHeight -> if (inc) -fullHeight else fullHeight }

    fun slideOut(inc: Boolean) =
        slideOutVertically(animationSpec = getTween()) { fullHeight -> if (inc) fullHeight else -fullHeight }

    fun enterTransition(inc: Boolean) = slideIn(inc) + fadeIn
    fun exitTransition(inc: Boolean) = slideOut(inc) + fadeOut

    val sizeTransform = SizeTransform(clip = false)

    Box(Modifier, contentAlignment = Alignment.Center) {
        AnimatedContent(
            targetState = value,
            transitionSpec = {
                val isIncrementing = targetState > initialState
                enterTransition(isIncrementing) togetherWith exitTransition(isIncrementing) using sizeTransform
            }
        ) { displayedValue ->
            content(displayedValue)
        }
    }
}
