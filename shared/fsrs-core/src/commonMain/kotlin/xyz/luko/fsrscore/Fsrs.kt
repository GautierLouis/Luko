package xyz.luko.fsrscore

import kotlin.math.exp
import kotlin.math.min
import kotlin.math.pow

/**
 * Implementation of the Free Spaced Repetition Scheduler (FSRS) algorithm version 6.
 *
 * This object provides the core mathematical functions to calculate memory stability,
 * item difficulty, and optimal review intervals based on the FSRS-v6 model. It uses
 * a set of 21 weights (W) to model memory decay and reinforcement.
 *
 * Key components:
 * - **Stability (S)**: The number of days it takes for the probability of recall to drop to 90%.
 * - **Difficulty (D)**: A scale from 1 to 10 representing how hard an item is to remember.
 * - **Retrievability (R)**: The probability of successfully recalling an item at a given time.
 *
 * @see <a href="https://github.com/open-spaced-repetition/fsrs4anki">FSRS Algorithm Specification</a>
 * @see <a href="https://github.com/open-spaced-repetition/awesome-fsrs/wiki/The-Algorithm">The Algorithm</a>
 */
object Fsrs {

    val W = doubleArrayOf(
        0.212, 1.2931, 2.3065, 8.2956, 6.4133, 0.8334, 3.0194, 0.001, 1.8722, 0.1666,
        0.796, 1.4835, 0.0614, 0.2629, 1.6483, 0.6014, 1.8729, 0.5425, 0.0912, 0.0658, 0.1542,
    )

    private val decay: Double get() = -W[20]
    private val factor: Double get() = 0.9.pow(1.0 / decay) - 1.0

    fun retrievability(elapsedDays: Double, stability: Double): Double =
        (1.0 + factor * (elapsedDays / stability)).pow(decay)

    fun interval(desiredRetention: Double, stability: Double): Double =
        (stability / factor) * (desiredRetention.pow(1.0 / decay) - 1.0)

    private fun clampD(d: Double): Double = d.coerceIn(1.0, 10.0)

    fun initialStability(grade: Grade): Double = when (grade) {
        Grade.FORGOT -> W[0]
        Grade.HARD -> W[1]
        Grade.GOOD -> W[2]
        Grade.EASY -> W[3]
    }

    fun initialDifficulty(grade: Grade): Double =
        clampD(W[4] - exp(W[5] * (grade.value - 1.0)) + 1.0)

    private fun deltaDifficulty(grade: Grade): Double = -W[6] * (grade.value - 3.0)

    private fun nextDifficultyRaw(d: Double, grade: Grade): Double =
        d + deltaDifficulty(grade) * ((10.0 - d) / 9.0)

    fun nextDifficulty(d: Double, grade: Grade): Double =
        clampD(W[7] * initialDifficulty(Grade.EASY) + (1.0 - W[7]) * nextDifficultyRaw(d, grade))

    private fun stabilityOnSuccess(d: Double, s: Double, r: Double, grade: Grade): Double {
        val tD = 11.0 - d
        val tS = s.pow(-W[9])
        val tR = exp(W[10] * (1.0 - r)) - 1.0
        val h = if (grade == Grade.HARD) W[15] else 1.0
        val b = if (grade == Grade.EASY) W[16] else 1.0
        val alpha = 1.0 + tD * tS * tR * h * b * exp(W[8])
        return s * alpha
    }

    private fun stabilityOnFailure(d: Double, s: Double, r: Double): Double {
        val dF = d.pow(-W[12])
        val sF = (s + 1.0).pow(W[13]) - 1.0
        val rF = exp(W[14] * (1.0 - r))
        return min(dF * sF * rF * W[11], s)
    }

    fun nextStability(d: Double, s: Double, r: Double, grade: Grade): Double =
        if (grade == Grade.FORGOT) stabilityOnFailure(d, s, r) else stabilityOnSuccess(
            d,
            s,
            r,
            grade
        )
}
