package com.louisgautier.dictionary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.louisgautier.designsystem.AppTitle
import com.louisgautier.designsystem.ai.Green50
import com.louisgautier.designsystem.preview.BooleanProvider
import com.louisgautier.domain.previewDictionaryWithGraphic
import com.louisgautier.domain.previewSimpleDataList
import com.louisgautier.utils.AppNavigation
import com.louisgautier.utils.Route
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionaryScreen(
    viewModel: DictionaryListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DictionaryScreen(
        state = state,
        onTabSelected = { viewModel.setSelectedTab(it) },
        onItemClick = { viewModel.setSelectedDictionary(it) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionaryScreen(
    state: DictionaryListViewModel.UIState,
    onTabSelected: (DictionaryTab) -> Unit,
    onItemClick: (Int?) -> Unit,
) {

    val pagerState = rememberPagerState { DictionaryTab.entries.size }

    val pagingItemsMap = remember(state.dictionaries) {
        DictionaryTab.entries.associateWith { tab -> state.dictionaries[tab] ?: emptyFlow() }
    }.mapValues { (_, flow) ->
        flow.collectAsLazyPagingItems()
    }

    val globalError by remember {
        derivedStateOf {
            pagingItemsMap.values.firstNotNullOfOrNull { items ->
                if (items.loadState.refresh is LoadState.Error && items.itemCount == 0) {
                    (items.loadState.refresh as LoadState.Error).error
                } else null
            }
        }
    }

    //Trigger when tab clicked
    LaunchedEffect(state.selectedTab) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    //Trigger when page is changed from scrolling
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { page ->
            onTabSelected(DictionaryTab.entries[page])
        }
    }

    if (state.selectedDictionary != null) {
        ModalCharacterDetails(
            character = state.selectedDictionary,
            modifier = Modifier,
            onDismiss = { onItemClick(null) },
            onPractice = { AppNavigation.navigate(Route.PracticeCharacterRoute(state.selectedDictionary.code)) }
        )
    }

    Scaffold(
        topBar = { AppTitle("Dictionary") },
        containerColor = Green50
    ) {
        Box(
            modifier = Modifier.padding(it),
        ) {
            HorizontalPager(
                state = pagerState,
                beyondViewportPageCount = 2,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 44.dp),
            ) { page ->
                val tab = DictionaryTab.entries[page]
                DictionaryPage(
                    items = pagingItemsMap[tab]!!,
                    modifier = Modifier.fillMaxSize(),
                    onItemClick = onItemClick
                )
            }

            if (globalError == null) {
                CharacterFrequencyLevelPicker(
                    selectedTabIndex = state.selectedTabIndex,
                    onTabSelected = onTabSelected,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun CharactersListPreview(
    @PreviewParameter(BooleanProvider::class) showModal: Boolean
) {
    DictionaryScreen(
        state = DictionaryListViewModel.UIState(
            dictionaries = mapOf(
                DictionaryTab.COMMON to flowOf(PagingData.from(previewSimpleDataList))
            ),
            selectedDictionary = if (showModal) previewDictionaryWithGraphic else null
        ),
        onTabSelected = {},
        onItemClick = {}
    )
}
