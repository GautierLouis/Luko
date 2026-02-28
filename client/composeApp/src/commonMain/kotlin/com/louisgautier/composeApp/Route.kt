package com.louisgautier.composeApp

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevel
import com.louisgautier.composeApp.session.QuestionCount
import com.louisgautier.domain.model.Difficulty
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

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