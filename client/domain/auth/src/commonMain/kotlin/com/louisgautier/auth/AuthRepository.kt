package com.louisgautier.auth

interface AuthRepository {
    //suspend fun login(email: String, password: String): Result<Unit>
//    suspend fun signUpAnonymously(): Result<FirebaseUser>
//    suspend fun updateFcmToken(fcmToken: String): Result<Unit>
//    fun getCurrentUser(): FirebaseUser?
//    fun isUserSignedIn(): Boolean
}

//class UpdateFcmTokenUseCase(
//    private val authRepository: AuthRepository
//) {
//    suspend operator fun invoke(fcmToken: String): Result<Unit> {
//        return authRepository.updateFcmToken(fcmToken)
//    }
//}