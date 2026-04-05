package com.louisgautier.firebase.event

sealed class TrackingEvent(
    val key: String,
    val params: MutableMap<String, Any> = mutableMapOf()
) {

    data class CreateSession(
        val difficulty: String,
        val nbQuestions: Int,
        val characterLevels: List<String>
    ) : TrackingEvent(
        key = "create_session",
        params = mutableMapOf(
            "difficulty" to difficulty,
            "nb_questions" to nbQuestions,
            "character_levels" to characterLevels.joinToString(",")
        )
    )
}