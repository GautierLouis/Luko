package xyz.luko.tracking

sealed class TrackingEvent(
    val key: String,
    val params: MutableMap<String, Any> = mutableMapOf(),
) {
    data class CreateSession(
        val trackingId: String,
        val startDate: String,
        val difficulty: String,
        val levels: String,
        val questions: List<Int>
    ) : TrackingEvent(
        key = "create_session",
        params = mutableMapOf(
            "session_id" to trackingId,
            "start_date" to startDate,
            "difficulty" to difficulty,
            "levels" to levels,
        )
    )

    data class SessionFinish(
        val trackingId: String,
        val endDate: String,
        val duration: Long,
        val difficulty: String,
        val levels: String,
        val score: Int,
        val responses: Map<Int, Float>
    ) : TrackingEvent(
        key = "finish_session",
        params =
            mutableMapOf(
                "session_id" to trackingId,
                "end_date" to endDate,
                "duration" to duration,
                "difficulty" to difficulty,
                "levels" to levels,
                "score" to score,
                "responses" to responses
            ),
    )

    data class NavigateTo(
        val name: String,
    ) : TrackingEvent(
        key = "navigate_to",
        params =
            mutableMapOf(
                "route" to name,
            ),
    )
}
