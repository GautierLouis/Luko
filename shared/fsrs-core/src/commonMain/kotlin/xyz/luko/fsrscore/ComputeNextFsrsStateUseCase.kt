package xyz.luko.fsrscore

import kotlin.math.roundToInt

class ComputeNextFsrsStateUseCase {
    /**
     * A use case that calculates the next [FsrsState] and the recommended review interval
     * based on the FSRS-v6 algorithm.
     *
     * This class handles both new items (where [current] is null) and existing items,
     * determining how memory stability and difficulty evolve after a review.
     *
     * @param current The current FSRS state of the item, or null if it's being reviewed for the first time.
     * @param grade The user's performance on the current review (e.g., Forgot, Good).
     * @param elapsedDays The number of days passed since the last review.
     * @param desiredRetention The target probability of recall (0.0 to 1.0) used to calculate
     * the next interval. Defaults to 0.9.
     */
    operator fun invoke(
        current: FsrsState?,
        grade: Grade,
        elapsedDays: Double,
        desiredRetention: Double = 0.9,
    ): Pair<FsrsState, Int> {
        require(elapsedDays >= 0.0) { "elapsedDays must not be negative, was $elapsedDays" }
        require(current == null || current.stability > 0.0) {
            "stability must be positive, was ${current?.stability}"
        }

        val newState = if (current == null) {
            FsrsState(
                difficulty = Fsrs.initialDifficulty(grade),
                stability = Fsrs.initialStability(grade),
            )
        } else {
            val r = Fsrs.retrievability(elapsedDays, current.stability)
            FsrsState(
                difficulty = Fsrs.nextDifficulty(current.difficulty, grade),
                stability = Fsrs.nextStability(current.difficulty, current.stability, r, grade),
            )
        }
        val nextIntervalDays = Fsrs.interval(desiredRetention, newState.stability)
            .roundToInt()
            .coerceAtLeast(1)

        return newState to nextIntervalDays
    }
}
