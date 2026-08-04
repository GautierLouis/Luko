package xyz.luko.fsrscore

import xyz.luko.fsrscore.GradeUseCase.deriveGrade
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class ComputeNextFsrsStateUseCaseTest {

    private val useCase = ComputeNextFsrsStateUseCase()
    private val epsilon = 0.001

    private fun assertClose(expected: Double, actual: Double, message: String) {
        assertTrue(kotlin.math.abs(expected - actual) < epsilon,
            "$message: expected $expected but was $actual")
    }

    private val randomPercentage
        get() = Random.nextFloat().coerceIn(0f, 100f)

    private fun generateRandomSignal(): AttemptSignals {
        return AttemptSignals(
            recognitionResult = RecognitionResult.SUCCESS,
            strokeCountCorrect = true,
            strokeOrderCorrect = true,
            strokeComparison = StrokeComparisonResult(
                overallAccuracy = randomPercentage,
                strokeAccuracies = listOf(randomPercentage),
                orderAccuracy = randomPercentage,
                details = ComparisonDetails(
                    pathSimilarity = randomPercentage,
                    startPointAccuracy = randomPercentage,
                    endPointAccuracy = randomPercentage,
                    directionAccuracy = randomPercentage,
                    orderPenalty = 0f,
                )
            ),
            durationMs = 1000,
            referenceStrokeCount = 1,
            practiceMode = PracticeMode.EASY,
            complexityFactor = 1.0f,
        )
    }

    @Test
    fun `sample`() {
        val claudeFsrs = ComputeNextFsrsStateUseCase()

        val signal1 = deriveGrade(generateRandomSignal())
        val signal2 = deriveGrade(generateRandomSignal())
        val signal3 = deriveGrade(generateRandomSignal())
        val signal4 = deriveGrade(generateRandomSignal())
        val signal5 = deriveGrade(generateRandomSignal())
        val signal6 = deriveGrade(generateRandomSignal())
        val signal7 = deriveGrade(generateRandomSignal())
        val signal8 = deriveGrade(generateRandomSignal())
        val signal9 = deriveGrade(generateRandomSignal())
        val signal10 = deriveGrade(generateRandomSignal())

        val attempt1 = claudeFsrs.invoke(   current = null,           grade = signal1, elapsedDays = 0.0)
        val attempt2 = claudeFsrs.invoke(   current = attempt1.first, grade = signal2, elapsedDays = attempt1.second.toDouble())
        val attempt3 = claudeFsrs.invoke(   current = attempt2.first, grade = signal3, elapsedDays = attempt2.second.toDouble())
        val attempt4 = claudeFsrs.invoke(   current = attempt3.first, grade = signal4, elapsedDays = attempt3.second.toDouble())
        val attempt5 = claudeFsrs.invoke(   current = attempt4.first, grade = signal5, elapsedDays = attempt4.second.toDouble())
        val attempt6 = claudeFsrs.invoke(   current = attempt5.first, grade = signal6, elapsedDays = attempt5.second.toDouble())
        val attempt7 = claudeFsrs.invoke(   current = attempt6.first, grade = signal7, elapsedDays = attempt6.second.toDouble())
        val attempt8 = claudeFsrs.invoke(   current = attempt7.first, grade = signal8, elapsedDays = attempt7.second.toDouble())
        val attempt9 = claudeFsrs.invoke(   current = attempt8.first, grade = signal9, elapsedDays = attempt8.second.toDouble())
        val attempt10 = claudeFsrs.invoke(  current = attempt9.first, grade = signal10, elapsedDays = attempt9.second.toDouble())

        val s = listOf(signal1, signal2, signal3, signal4, signal5, signal6, signal7, signal8, signal9, signal10)
        val r = listOf(attempt1, attempt2, attempt3, attempt4, attempt5, attempt6, attempt7, attempt8, attempt9, attempt10)

        r.forEachIndexed { index, pair ->
            println(s[index])
            println(pair)
        }

    }

    // --- First review, all four grades, verified against reference computation ---

    @Test
    fun `first review FORGOT`() {
        val (state, interval) = useCase(null, Grade.FORGOT, 0.0)
        assertClose(6.413300, state.difficulty, "difficulty")
        assertClose(0.212000, state.stability, "stability")
        assertEquals(1, interval)
    }

    @Test
    fun `first review HARD`() {
        val (state, interval) = useCase(null, Grade.HARD, 0.0)
        assertClose(5.112171, state.difficulty, "difficulty")
        assertClose(1.293100, state.stability, "stability")
        assertEquals(1, interval)
    }

    @Test
    fun `first review GOOD`() {
        val (state, interval) = useCase(null, Grade.GOOD, 0.0)
        assertClose(2.118104, state.difficulty, "difficulty")
        assertClose(2.306500, state.stability, "stability")
        assertEquals(2, interval)
    }

    @Test
    fun `first review EASY`() {
        val (state, interval) = useCase(null, Grade.EASY, 0.0)
        assertClose(1.000000, state.difficulty, "difficulty")
        assertClose(8.295600, state.stability, "stability")
        assertEquals(8, interval)
    }

    // --- Second review, all four grades following an initial GOOD ---

    private fun afterFirstGood() = useCase(null, Grade.GOOD, 0.0)

    @Test
    fun `second review FORGOT after GOOD sharply reduces stability`() {
        val (first, interval0) = afterFirstGood()
        val (state, interval) = useCase(first, Grade.FORGOT, interval0.toDouble())

        assertClose(7.400274, state.difficulty, "difficulty")
        assertClose(0.607580, state.stability, "stability")
        assertEquals(1, interval)
        assertTrue(state.stability < first.stability)
        assertTrue(state.difficulty > first.difficulty)
    }

    @Test
    fun `second review HARD after GOOD`() {
        val (first, interval0) = afterFirstGood()
        val (state, interval) = useCase(first, Grade.HARD, interval0.toDouble())

        assertClose(4.758630, state.difficulty, "difficulty")
        assertClose(7.513320, state.stability, "stability")
        assertEquals(8, interval)
    }

    @Test
    fun `second review GOOD after GOOD`() {
        val (first, interval0) = afterFirstGood()
        val (state, interval) = useCase(first, Grade.GOOD, interval0.toDouble())

        assertClose(2.116986, state.difficulty, "difficulty")
        assertClose(10.964332, state.stability, "stability")
        assertEquals(11, interval)
        assertTrue(state.stability > first.stability)
    }

    @Test
    fun `second review EASY after GOOD grows stability most`() {
        val (first, interval0) = afterFirstGood()
        val (hard, _) = useCase(first, Grade.HARD, interval0.toDouble())
        val (good, _) = useCase(first, Grade.GOOD, interval0.toDouble())
        val (easy, _) = useCase(first, Grade.EASY, interval0.toDouble())

        assertClose(1.000000, easy.difficulty, "difficulty")
        assertClose(18.521754, easy.stability, "stability")
        assertTrue(hard.stability < good.stability)
        assertTrue(good.stability < easy.stability)
    }

    // --- Edge cases ---

    @Test
    fun `negative elapsedDays throws`() {
        val (first, _) = afterFirstGood()
        assertFailsWith<IllegalArgumentException> {
            useCase(first, Grade.GOOD, -5.0)
        }
    }

    @Test
    fun `zero or negative stability throws rather than crashing`() {
        val corrupted = FsrsState(difficulty = 5.0, stability = 0.0)
        assertFailsWith<IllegalArgumentException> {
            useCase(corrupted, Grade.GOOD, 3.0)
        }
    }

    @Test
    fun `very large elapsedDays never produces NaN and stays bounded`() {
        val (first, _) = afterFirstGood()
        val (state, interval) = useCase(first, Grade.GOOD, 100_000.0)

        assertFalse(state.stability.isNaN())
        assertFalse(state.difficulty.isNaN())
        assertClose(2.116986, state.difficulty, "difficulty")
        assertClose(106.671587, state.stability, "stability")
        assertEquals(107, interval)
    }

    @Test
    fun `many consecutive failures converge to a positive floor, never zero or negative`() {
        var state = useCase(null, Grade.GOOD, 0.0).first
        repeat(200) {
            state = useCase(state, Grade.FORGOT, 1.0).first
        }
        assertClose(0.0009477601, state.stability, "stability")
        assertClose(9.9866, state.difficulty, "difficulty")
        assertTrue(state.stability > 0.0, "stability must never reach zero or negative")
        assertTrue(state.difficulty <= 10.0, "difficulty must stay capped at 10")
    }

    @Test
    fun `difficulty always stays within 1 to 10 across all first-review grades`() {
        Grade.entries.forEach { grade ->
            val (state, _) = useCase(null, grade, 0.0)
            assertTrue(state.difficulty in 1.0..10.0, "out of range for $grade: ${state.difficulty}")
        }
    }

    @Test
    fun `interval is never less than 1 day`() {
        val (first, _) = afterFirstGood()
        val (_, interval) = useCase(first, Grade.FORGOT, 1.0)
        assertTrue(interval >= 1)
    }
}
