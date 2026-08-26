package xyz.luko.learning.congratulation.levelup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import xyz.luko.domain.model.Level
import xyz.luko.learning.congratulation.EndOfSessionCoordinator
import xyz.luko.ui.navigation.AppRoute

internal class LevelUpViewModel(
    params: AppRoute.Learning.LevelUp,
    private val coordinator: EndOfSessionCoordinator
) : ViewModel() {

    data class UiState(
        val levels: List<LevelUp>
    )

    val state: StateFlow<UiState>
        field = MutableStateFlow(
            UiState(params.levels.toLevelUps { code -> Char(code).toString() })
        )

    private fun Map<Int, List<Int>>.toLevelUps(
        resolveCharacter: (Int) -> String
    ): List<LevelUp> = map { (levelIndex, characterIds) ->
        LevelUp(
            previousLevel = Level.fromRank(levelIndex - 1),
            currentLevel = Level.fromRank(levelIndex),
            characters = characterIds.map(resolveCharacter)
        )
    }.filter { it.currentLevel != Level.NEVER_PRACTICED }
        .sortedBy { it.currentLevel.rank }

    fun next() {
        coordinator.next()
    }
}
