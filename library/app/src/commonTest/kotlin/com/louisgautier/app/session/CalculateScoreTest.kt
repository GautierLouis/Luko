package com.louisgautier.app.session

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevelDto
import com.louisgautier.domain.model.Dictionary
import com.louisgautier.domain.model.DifficultyLevel
import com.louisgautier.learning.builder.QuestionCount
import com.louisgautier.learning.session.usecase.CalculateScore
import com.louisgautier.learning.session.usecase.CalculateScore.ScoreDefault
import kotlin.test.Test
import kotlin.test.assertTrue

class CalculateScoreTest {

    private val computer = CalculateScore()

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
            difficulty = DifficultyLevel.EASY,
            timeElapsed = Long.MAX_VALUE
        )

        assertTrue(score == ScoreDefault.BASE_MIN_POINT)

    }

    @Test
    fun `MEDIUM difficulty base score calculation`() {
        val questions = generateQuestion(CharacterFrequencyLevelDto.COMMON, QuestionCount.FIVE)

        val score = computer.calculate(
            questions = questions,
            difficulty = DifficultyLevel.MEDIUM,
            timeElapsed = Long.MAX_VALUE
        )

        assertTrue(score == 75)

    }

    @Test
    fun `HARD difficulty base score calculation`() {
        val questions = generateQuestion(CharacterFrequencyLevelDto.COMMON, QuestionCount.FIVE)

        val score = computer.calculate(
            questions = questions,
            difficulty = DifficultyLevel.HARD,
            timeElapsed = Long.MAX_VALUE
        )

        assertTrue(score == 100)

    }

    @Test
    fun `Calculate max points`() {
        val questions = generateQuestion(CharacterFrequencyLevelDto.EXTENDED, QuestionCount.TWENTY)

        val score = computer.calculate(
            questions = questions,
            difficulty = DifficultyLevel.HARD,
            timeElapsed = Long.MAX_VALUE
        )

        assertTrue(score == ScoreDefault.BASE_MAX_POINT)
    }

    @Test
    fun `Calculate min points`() {
        val questions = generateQuestion(CharacterFrequencyLevelDto.COMMON, QuestionCount.FIVE)

        val score = computer.calculate(
            questions = questions,
            difficulty = DifficultyLevel.EASY,
            timeElapsed = Long.MAX_VALUE
        )

        assertTrue(score == ScoreDefault.BASE_MIN_POINT)
    }

    @Test
    fun `Calculate time bonus at lower`() {
        val questions = generateQuestion(CharacterFrequencyLevelDto.COMMON, QuestionCount.FIVE)

        val score = computer.calculate(
            questions = questions,
            difficulty = DifficultyLevel.EASY,
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
            difficulty = DifficultyLevel.EASY,
            timeElapsed = (5_000 * QuestionCount.FIVE.value).toLong()
        )

        //check only bonus
        val bonus = score - ScoreDefault.BASE_MIN_POINT

        assertTrue(bonus == ScoreDefault.TIME_MAX_POINT)
    }
}