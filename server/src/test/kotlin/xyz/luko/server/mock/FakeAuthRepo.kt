package xyz.luko.server.mock

import xyz.luko.apicontracts.dto.AuthRegistrationDto
import xyz.luko.apicontracts.dto.FcmUpdateDto
import xyz.luko.server.domain.repo.UserRepository

class FakeAuthRepo : UserRepository {

    override suspend fun registerAnonymously(
        uid: String,
        body: AuthRegistrationDto
    ) {

    }

    override suspend fun updateFcm(
        uid: String,
        body: FcmUpdateDto
    ) {

    }
}
