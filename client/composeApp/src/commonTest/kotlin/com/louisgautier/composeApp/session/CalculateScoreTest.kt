package com.louisgautier.composeApp.session

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevelDto
import com.louisgautier.domain.model.Dictionary
import com.louisgautier.domain.model.Difficulty
import com.louisgautier.learning.CalculateScore
import com.louisgautier.learning.builder.QuestionCount
import com.louisgautier.learning.ScoreDefault
import kotlin.test.Test
import kotlin.test.assertTrue

class CalculateScoreTest {

    val computer = CalculateScore()

    /**
     * Utility method to generate fake questions for testing.
     *
     */
    private fun generateQuestion(
        level: CharacterFrequencyLevelDto,
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

        val questions = generateQuestion(CharacterFrequencyLevelDto.COMMON, QuestionCount.FIVE)

        val score = computer.calculate(
            questions = questions,
            difficulty = Difficulty.EASY,
            timeElapsed = Long.MAX_VALUE
        )

        assertTrue(score == ScoreDefault.BASE_MIN_POINT)

    }

    @Test
    fun `MEDIUM difficulty base score calculation`() {
        val questions = generateQuestion(CharacterFrequencyLevelDto.COMMON, QuestionCount.FIVE)

        val score = computer.calculate(
            questions = questions,
            difficulty = Difficulty.MEDIUM,
            timeElapsed = Long.MAX_VALUE
        )

        assertTrue(score == 75)

    }

    @Test
    fun `HARD difficulty base score calculation`() {
        val questions = generateQuestion(CharacterFrequencyLevelDto.COMMON, QuestionCount.FIVE)

        val score = computer.calculate(
            questions = questions,
            difficulty = Difficulty.HARD,
            timeElapsed = Long.MAX_VALUE
        )

        assertTrue(score == 100)

    }

    @Test
    fun `Calculate max points`() {
        val questions = generateQuestion(CharacterFrequencyLevelDto.EXTENDED, QuestionCount.TWENTY)

        val score = computer.calculate(
            questions = questions,
            difficulty = Difficulty.HARD,
            timeElapsed = Long.MAX_VALUE
        )

        assertTrue(score == ScoreDefault.BASE_MAX_POINT)
    }

    @Test
    fun `Calculate min points`() {
        val questions = generateQuestion(CharacterFrequencyLevelDto.COMMON, QuestionCount.FIVE)

        val score = computer.calculate(
            questions = questions,
            difficulty = Difficulty.EASY,
            timeElapsed = Long.MAX_VALUE
        )

        assertTrue(score == ScoreDefault.BASE_MIN_POINT)
    }

    @Test
    fun `Calculate time bonus at lower`() {
        val questions = generateQuestion(CharacterFrequencyLevelDto.COMMON, QuestionCount.FIVE)

        val score = computer.calculate(
            questions = questions,
            difficulty = Difficulty.EASY,
            timeElapsed = (10_000 * QuestionCount.FIVE.value).toLong()
        )

        //check only bonus
        val bonus = score - ScoreDefault.BASE_MIN_POINT

        assertTrue(bonus == ScoreDefault.TIME_MIN_POINT)
    }

    @Test
    fun `Calculate time bonus at max`() {
        val questions = generateQuestion(CharacterFrequencyLevelDto.COMMON, QuestionCount.FIVE)

        val score = computer.calculate(
            questions = questions,
            difficulty = Difficulty.EASY,
            timeElapsed = (5_000 * QuestionCount.FIVE.value).toLong()
        )

        //check only bonus
        val bonus = score - ScoreDefault.BASE_MIN_POINT

        assertTrue(bonus == ScoreDefault.TIME_MAX_POINT)
    }
}