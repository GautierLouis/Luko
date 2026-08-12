package xyz.luko.server.domain.repo

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import xyz.luko.apicontracts.dto.AuthRegistrationDto
import xyz.luko.apicontracts.dto.FcmUpdateDto
import xyz.luko.apicontracts.dto.UserDto
import xyz.luko.server.data.database.dao.UserDao
import xyz.luko.server.data.database.table.UserTable
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
    suspend fun getUserId(id: String): EntityID<Int>?
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
        dao.updateFcm(body.toRow(uid))
    }

    override suspend fun getUser(id: String): UserDto? {
        return dao.getByFID(id)?.toUserDto()
    }

    override suspend fun getUserId(id: String): EntityID<Int>? {
        return dao.getByFID(id)?.get(UserTable.id)
    }
}
