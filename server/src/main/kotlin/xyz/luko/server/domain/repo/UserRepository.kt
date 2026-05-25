package xyz.luko.server.domain.repo

import xyz.luko.apicontracts.dto.AuthRegistrationDto
import xyz.luko.apicontracts.dto.FcmUpdateDto
import xyz.luko.server.data.database.dao.UserDao
import xyz.luko.server.domain.mapper.DomainMapping.toRow


interface UserRepository {
    suspend fun registerAnonymously(
        uid: String,
        body: AuthRegistrationDto
    )

    suspend fun updateFcm(
        uid: String,
        body: FcmUpdateDto
    )
}


// --- Implementation ---

internal class DefaultUserRepository(
    private val dao: UserDao,
) : UserRepository {

    override suspend fun registerAnonymously(
        uid: String,
        body: AuthRegistrationDto
    ) {
        dao.insertUser(body.toRow(uid))
    }

    override suspend fun updateFcm(
        uid: String,
        body: FcmUpdateDto
    ) {
        dao.updateUser(body.toRow(uid))
    }
}
