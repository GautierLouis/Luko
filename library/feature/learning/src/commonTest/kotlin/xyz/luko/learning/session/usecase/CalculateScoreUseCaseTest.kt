package xyz.luko.learning.session.usecase

import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.Dictionary
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.learning.builder.QuestionCount
import kotlin.test.Test
import kotlin.test.assertEquals


class CalculateScoreUseCaseTest {

    private val computer = CalculateScoreUseCase()

    /**
     * Utility method to generate fake questions for testing.
     *
     */
    private fun generateQuestion(
        level: CharacterFrequencyLevel,
        count: QuestionCount
    ): List<Dictionary> {
        return List(count.value) {
            Dictionary(
                code = 'a'.code,
                level = level
            )
        }
    }

    @Test
    fun `EASY difficulty base score calculation`() {

        val questions = generateQuestion(CharacterFrequencyLevel.COMMON, QuestionCount.FIVE)

        val score = computer.calculate(
            questions = questions,
            difficulty = DifficultyLevel.EASY,
            timeElapsed = Long.MAX_VALUE
        )

        assertEquals(score, CalculateScoreUseCase.ScoreDefault.BASE_MIN_POINT)

    }

    @Test
    fun `MEDIUM difficulty base score calculation`() {
        val questions = generateQuestion(CharacterFrequencyLevel.COMMON, QuestionCount.FIVE)

        val score = computer.calculate(
            questions = questions,
            difficulty = DifficultyLevel.MEDIUM,
            timeElapsed = Long.MAX_VALUE
        )

        assertEquals(score, 75)

    }

    @Test
    fun `HARD difficulty base score calculation`() {
        val questions = generateQuestion(CharacterFrequencyLevel.COMMON, QuestionCount.FIVE)

        val score = computer.calculate(
            questions = questions,
            difficulty = DifficultyLevel.HARD,
            timeElapsed = Long.MAX_VALUE
        )

        assertEquals(score, 100)

    }

    @Test
    fun `Calculate max points`() {
        val questions = generateQuestion(CharacterFrequencyLevel.STANDARD, QuestionCount.TWENTY)

        val score = computer.calculate(
            questions = questions,
            difficulty = DifficultyLevel.HARD,
            timeElapsed = Long.MAX_VALUE
        )

        assertEquals(score, CalculateScoreUseCase.ScoreDefault.BASE_MAX_POINT)
    }

    @Test
    fun `Calculate min points`() {
        val questions = generateQuestion(CharacterFrequencyLevel.COMMON, QuestionCount.FIVE)

        val score = computer.calculate(
            questions = questions,
            difficulty = DifficultyLevel.EASY,
            timeElapsed = Long.MAX_VALUE
        )

        assertEquals(score, CalculateScoreUseCase.ScoreDefault.BASE_MIN_POINT)
    }

    @Test
    fun `Calculate time bonus at lower`() {
        val questions = generateQuestion(CharacterFrequencyLevel.COMMON, QuestionCount.FIVE)

        val score = computer.calculate(
            questions = questions,
            difficulty = DifficultyLevel.EASY,
            timeElapsed = (10_000 * QuestionCount.FIVE.value).toLong()
        )

        //check only bonus
        val bonus = score - CalculateScoreUseCase.ScoreDefault.BASE_MIN_POINT

        assertEquals(bonus, CalculateScoreUseCase.ScoreDefault.TIME_MIN_POINT)
    }

    @Test
    fun `Calculate time bonus at max`() {
        val questions = generateQuestion(CharacterFrequencyLevel.COMMON, QuestionCount.FIVE)

        val score = computer.calculate(
            questions = questions,
            difficulty = DifficultyLevel.EASY,
            timeElapsed = (5_000 * QuestionCount.FIVE.value).toLong()
        )

        //check only bonus
        val bonus = score - CalculateScoreUseCase.ScoreDefault.BASE_MIN_POINT

        assertEquals(bonus, CalculateScoreUseCase.ScoreDefault.TIME_MAX_POINT)
    }
}