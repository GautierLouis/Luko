package xyz.luko.server.domain.usecase


class LevelUseCase {

    companion object {
        private val THRESHOLDS = listOf(0, 1, 3, 7, 21, 60, 180)
    }

    fun compute(
        stability: Double,
        currentLevel: Int?
    ): Pair<Int, Boolean> {
        val newLevel = THRESHOLDS.indexOfLast { stability >= it }.coerceAtLeast(0)

        return newLevel to (newLevel > (currentLevel ?: 0))
    }

}
