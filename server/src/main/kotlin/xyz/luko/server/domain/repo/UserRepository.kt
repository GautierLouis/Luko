package xyz.luko.server.domain.repo

import xyz.luko.apicontracts.dto.AuthRegistrationDto
import xyz.luko.apicontracts.dto.FcmUpdateDto
import xyz.luko.apicontracts.dto.UserDto
import xyz.luko.server.data.database.dao.UserDao
import xyz.luko.server.domain.mapper.DomainMapping.toRow
import xyz.luko.server.domain.mapper.ResultRowMapping.toUserDto

interface UserRepository {
    suspend fun registerAnonymously(
        uid: String,
        platform: String,
        body: AuthRegistrationDto
    )

    suspend fun updateFcm(
        uid: String,
        body: FcmUpdateDto
    )

    suspend fun getUser(id: String): UserDto?
}


// --- Implementation ---

internal class DefaultUserRepository(
    private val dao: UserDao,
) : UserRepository {

    override suspend fun registerAnonymously(
        uid: String,
        platform: String,
        body: AuthRegistrationDto
    ) {
        dao.insertUser(body.toRow(uid, platform))
    }

    override suspend fun updateFcm(
        uid: String,
        body: FcmUpdateDto
    ) {
        dao.updateUser(body.toRow(uid))
    }

    override suspend fun getUser(id: String): UserDto? {
        return dao.getById(id)?.toUserDto()
    }
}
