package xyz.luko.ui.designsystem.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalInspectionMode
import xyz.luko.ui.designsystem.theme.LocalAnimatedContentScope
import xyz.luko.ui.designsystem.theme.LocalSharedTransitionScope

fun Modifier.sharedElement(key: Any): Modifier = composed {
    if (LocalInspectionMode.current) return@composed this
    with(LocalSharedTransitionScope.current) {
        sharedElement(
            sharedContentState = rememberSharedContentState(key),
            animatedVisibilityScope = LocalAnimatedContentScope.current,
        )
    }
}

fun Modifier.sharedBounds(
    key: Any,
): Modifier = composed {
    if (LocalInspectionMode.current) return@composed this
    with(LocalSharedTransitionScope.current) {
        sharedBounds(
            sharedContentState = rememberSharedContentState(key),
            animatedVisibilityScope = LocalAnimatedContentScope.current,
        )
    }
}
