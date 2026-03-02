package com.louisgautier.utils

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object HomeRoute : Route()

    @Serializable
    data object DictionaryListRoute : Route()

    @Serializable
    data object SessionBuilderRoute : Route()

    @Serializable
    data class SessionRoute(
        val levels: String,
        val difficulty: String,
        val limit: String,
    ) : Route()

    @Serializable
    data object SessionCongratulationScreenRoute : Route()

    @Serializable
    data class PracticeCharacterRoute(val code: Int) : Route()

}