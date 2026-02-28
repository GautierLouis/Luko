package com.louisgautier.auth

import com.louisgautier.apicontracts.dto.AuthUserJson
import com.louisgautier.firebase.FirebaseManager
import com.louisgautier.network.interfaces.AuthService
import com.louisgautier.preferences.AppPreferences

class DefaultAuthRepository(
    private val client: AuthService,
    private val preferences: AppPreferences,
    private val firebaseManager: FirebaseManager
) : AuthRepository {
    //    override suspend fun login(email: String, password: String): Result<Unit> =
//        client
//            .login(AuthUserJson(email, password))
//            .onSuccess { (token, refresh) ->
//                preferences.setUserToken(token)
//                preferences.setUserRefreshToken(refresh)
//            }.map {}
//    override suspend fun signUpAnonymously(): Result<FirebaseUser> {
//    }
//
//    override suspend fun updateFcmToken(fcmToken: String): Result<Unit> {
//        TODO("Not yet implemented")
//    }
//
//    override fun getCurrentUser(): FirebaseUser? {
//        TODO("Not yet implemented")
//    }
//
//    override fun isUserSignedIn(): Boolean {
//        TODO("Not yet implemented")
//    }
}

