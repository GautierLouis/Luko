package xyz.luko.learning.session_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import xyz.luko.baseui.drawing.StrokeTransformer
import xyz.luko.baseui.preview.PreviewProvider
import xyz.luko.baseui.session.toUiModel
import xyz.luko.designsystem.components.metrics.SessionCard
import xyz.luko.designsystem.components.page.LoadingScreen
import xyz.luko.designsystem.components.page.NestedScaffold
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.ShapeDefaults
import xyz.luko.designsystem.token.dimens.Spacing
import xyz.luko.domain.model.SessionResponse
import xyz.luko.learning.session.drawing.DrawableArea
import xyz.luko.navigation.AppRoute
import kotlin.math.roundToInt

@Composable
fun SessionDetailsScreen(route: AppRoute.LearningRoute.SessionDetailsRoute) {
    val viewModel = koinViewModel<SessionDetailsViewModel> {
        parametersOf(route)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    SessionDetailsScreen(state)
}

@Composable
private fun SessionDetailsScreen(state: SessionDetailsViewModel.UiState) {
    when (state) {
        SessionDetailsViewModel.UiState.Loading -> LoadingScreen()
        is SessionDetailsViewModel.UiState.Success -> SessionDetailsScreen(state)
    }
}

@Composable
private fun SessionDetailsScreen(
    state: SessionDetailsViewModel.UiState.Success
) {
    NestedScaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = Padding.large),
            verticalArrangement = Spacing.large,
        ) {
            item {
                SessionCard(
                    model = state.session.toUiModel(),
                    clickable = false,
                )
            }

            items(
                items = state.response,
                key = { it.code },
            ) {
                StrokesComparator(it)
            }
        }
    }
}

@Composable
private fun StrokesComparator(
    response: SessionResponse
) {
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }
    val transformer = remember { StrokeTransformer() }

    val referencePaths = remember(response.references, canvasSize) {
        response.references.map { transformer.toCanvasPath(it, canvasSize) }
    }

    val previousUserDraw = remember(response.strokes, canvasSize) {
        response.strokes.map { stroke ->
            val offsets = stroke.points.map { Offset(it.x, it.y) }
            transformer.toCanvasOffsets(offsets, canvasSize)
        }
    }

    fun Float.toPercent(): String = "${roundToInt()}%"

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = ShapeDefaults.card(),
        colors = CardDefaults.cardColors(
            containerColor = Theme.materialColors.surfaceContainer,
            contentColor = Theme.materialColors.onBackground,
        ),
    ) {
        Column(
            modifier = Modifier.padding(Padding.large),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = response.pinyin,
                    style = Theme.typography.titleLarge,
                )
                Text(
                    text = response.statistics.overallAccuracy.toPercent(),
                    style = Theme.typography.titleLarge,
                )
            }

            DrawableArea(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clipToBounds()
                    .onGloballyPositioned { canvasSize = it.size },
                referencePath = referencePaths,
                previousDrawnStrokes = previousUserDraw,
            )
        }
    }
}


@Preview
@Composable
private fun PreviewSessionDetailsScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        SessionDetailsScreen(
            state = SessionDetailsViewModel.UiState.Success(
                session = PreviewProvider.session,
                response = PreviewProvider.sessionResponse
            ),
        )
    }
}
