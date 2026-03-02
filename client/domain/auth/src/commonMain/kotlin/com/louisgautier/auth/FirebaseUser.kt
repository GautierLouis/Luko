package com.louisgautier.auth

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseUser(
    val userId: String,
    val fcmToken: String? = null
)