package xyz.luko.learning.congratulation

import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.ReviewResult
import xyz.luko.domain.model.Session
import xyz.luko.utils.DebugAction
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes

@Suppress("ClassName")
internal class DebugAction_TriggerEndOfSession(
    private val coordinator: EndOfSessionCoordinator,
) : DebugAction {
    override val label = "Test end-of-session flow"

    override suspend fun execute() {
        coordinator.prepareAndStart(
            reviewResult = ReviewResult(
                hasLevelUp = true,
                isStreakUpdated = true,
                newStreak = 5,
                levels = mapOf(
                    1 to listOf(25105, 20320, 20182),
                    2 to listOf(22909, 26159, 19981),
                    3 to listOf(20154, 22823, 23567),
                ),
                strokeComparison = emptyList()
            ),
            session = Session(
                id = 1L,
                date = Clock.System.now(),
                duration = 1.minutes,
                difficulty = DifficultyLevel.EASY,
                questionsCount = 5,
                accuracy = 80.0,
            )
        )
    }
}
