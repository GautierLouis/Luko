package xyz.luko.firebase

import xyz.luko.tracking.TrackingEvent

class DesktopFirebaseManager : FirebaseManager {

    //    private val client = HttpClient(CIO) {
    //        install(ContentNegotiation) { json() }
    //    }
//    private val apiKey = BuildConfig.FIREBASE_WEB_API_KEY


    override fun initialize() { /* Nothing — no Firebase SDK init on JVM desktop */
    }

    override suspend fun registerAnonymously(): Result<String> {
//        val response = client.post(
//            "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=$apiKey"
//        ) {
//            contentType(ContentType.Application.Json)
//            setBody(mapOf("returnSecureToken" to true))
//        }
//
//        val body = response.body<FirebaseSignUpResponse>()
//
//        return FirebaseAuthResult(
//            uid = body.localId,
//            idToken = body.idToken
//        )
        return Result.success("fake-user")
    }

    override suspend fun getIdToken(forceRefresh: Boolean) = Result.success("fake-token")

    override suspend fun getFCMToken(): Result<String> = Result.success("fake-fcm")


    override fun logEvent(event: TrackingEvent) {}

    override fun setUserId(userId: String) {}

    override fun setUserProperty(
        name: String,
        value: String,
    ) {
    }

    override fun fetchRemoteConfig() {
    }
}
