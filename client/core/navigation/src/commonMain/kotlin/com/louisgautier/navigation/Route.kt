package com.louisgautier.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
sealed interface Route: NavKey

@Serializable
data class MainKey(
    val defaultSelectedItem: Int = 0
) : Route

//TODO Split into feature Module ?
@Serializable
sealed interface LearningKey : Route
@Serializable
data object BuilderKey : LearningKey
@Serializable
data class SessionKey(
    val levels: String,
    val difficulty: String,
    val limit: String,
) : LearningKey

@Serializable
data object CongratulationKey : LearningKey
@Serializable
data class PracticeCharacterKey(val characterCode: Int) : LearningKey


val savedStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(MainKey::class, MainKey.serializer())

            subclass(BuilderKey::class, BuilderKey.serializer())
            subclass(SessionKey::class, SessionKey.serializer())
            subclass(CongratulationKey::class, CongratulationKey.serializer())

            subclass(PracticeCharacterKey::class, PracticeCharacterKey.serializer())
        }
    }
}