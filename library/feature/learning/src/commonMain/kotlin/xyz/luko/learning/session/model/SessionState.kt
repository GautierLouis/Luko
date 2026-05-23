package xyz.luko.learning.session.model

import xyz.luko.domain.model.Dictionary
import kotlin.time.Instant

sealed class SessionState {
    data object Error : SessionState()

    data object Loading : SessionState()

    data class Success(
        val startTime: Instant,
        val currentPageIndex: Int = 0,
        val questions: List<Dictionary> = emptyList(),
        val drawingPageState: Map<Int, DrawingPageState> = emptyMap(),
        val showLeaveDialog: Boolean = false,
    ) : SessionState() {
        // Get current question general information
        val currentQuestion = questions[currentPageIndex]

        // Get current pinyin for this question
        val pinyin = currentQuestion.pinyin.firstOrNull().orEmpty()

        // Get current question Char code. Unique per session
        val currentPageCode = currentQuestion.code

        // Get current question drawing information.
        val currentDrawingPageState = drawingPageState[currentPageCode]!!

        // Get current question drawing state. True when all strokes have been drawn
        val isPageCompleted = drawingPageState[currentPageCode]?.isComplete ?: false
        val isLastQuestion = currentPageIndex == questions.lastIndex
    }
}
