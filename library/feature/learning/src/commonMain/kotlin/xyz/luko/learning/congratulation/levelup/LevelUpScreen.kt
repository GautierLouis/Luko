package xyz.luko.learning.congratulation.levelup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import xyz.luko.domain.model.Level
import xyz.luko.ui.designsystem.components.button.AppButton
import xyz.luko.ui.designsystem.components.button.attrs.ButtonSize
import xyz.luko.ui.designsystem.components.page.NestedScaffold
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.navigation.AppRoute
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun LevelUpScreen(
    route: AppRoute.Learning.LevelUp
) {
    val viewModel = koinViewModel<LevelUpViewModel> { parametersOf(route) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    LevelUpScreen(state) {
        viewModel.next()
    }
}

@Composable
private fun LevelUpScreen(
    state: LevelUpViewModel.UiState,
    onClick: () -> Unit = {}
) {

    // Disable animation during preview
    val isPreview = LocalInspectionMode.current

    val listState: LazyListState = rememberLazyListState()
    var buttonHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val isScrollingUp = listState.isScrollingUp()


    NestedScaffold { paddingValues ->
        Box(Modifier.fillMaxSize().padding(paddingValues)) {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(bottom = buttonHeight + 16.dp),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                stickyHeader(key = "header") {
                    Text(
                        text = "LEVEL UP!",
                        style = Theme.typography.headlineLarge,
                        modifier = Modifier.fillMaxSize()
                            .padding(vertical = 24.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                items(
                    key = { index -> state.levels[index].currentLevel },
                    count = state.levels.size
                ) { index ->
                    val level = state.levels[index]
                    var visible by remember { mutableStateOf(isPreview) }

                    LaunchedEffect(Unit) {
                        delay((index * 100L).milliseconds)
                        visible = true
                    }

                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn(
                            animationSpec = tween(300)
                        ) + slideInVertically(
                            animationSpec = tween(300),
                            initialOffsetY = { it / 4 }
                        )
                    ) {
                        LevelCard(level)
                    }
                }
            }

            AnimatedVisibility(
                visible = isScrollingUp,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .onGloballyPositioned {
                        buttonHeight = with(density) { it.size.height.toDp() }
                    }
            ) {
                AppButton(
                    onClick = { onClick() },
                    modifier = Modifier.padding(16.dp),
                    text = "Continue",
                    size = ButtonSize.Large,
                )
            }
        }
    }
}

@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@Composable
private fun LevelCard(level: LevelUp) {

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                color = Theme.materialColors.surfaceContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = Theme.materialColors.outlineVariant
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {

        Row {
            Text(
                text = buildAnnotatedString {
                    if (level.previousLevel.rank > Level.NEVER_PRACTICED.rank) {
                        withStyle(
                            SpanStyle(
                                color = Theme.materialColors.error,
                                textDecoration = TextDecoration.LineThrough
                            )
                        ) {
                            append(level.previousLevel.name)
                        }
                        withStyle(SpanStyle(color = Theme.materialColors.onSurfaceVariant)) {
                            append(" \u2192 ")
                        }
                    }
                    withStyle(
                        SpanStyle(
                            color = Theme.materialColors.primary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(level.currentLevel.name)
                    }
                },
                style = Theme.typography.labelLarge
            )
        }

        FlowRow {
            level.characters.forEach { character ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(30.dp)
                        .background(
                            color = Theme.materialColors.secondaryContainer,
                            shape = RoundedCornerShape(2.dp)
                        )
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                color = Theme.materialColors.outline
                            ),
                            shape = RoundedCornerShape(2.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = character,
                        textAlign = TextAlign.Center,
                        style = Theme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLevelUpScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        LevelUpScreen(
            state = LevelUpViewModel.UiState(
                levels = listOf(
                    LevelUp(Level.NEVER_PRACTICED, Level.BEGINNER, listOf("我", "你", "他")),
                    LevelUp(Level.BEGINNER, Level.LEARNER, listOf("好", "是", "不")),
                    LevelUp(Level.LEARNER, Level.PRACTITIONER, listOf("人", "大", "小")),
                )
            )
        )
    }
}
