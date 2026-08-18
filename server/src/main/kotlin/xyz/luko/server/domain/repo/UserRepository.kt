package xyz.luko.server.domain.repo

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import xyz.luko.apicontracts.dto.AuthRegistrationDto
import xyz.luko.apicontracts.dto.FcmUpdateDto
import xyz.luko.apicontracts.dto.MeDto
import xyz.luko.server.data.database.dao.CharacterFsrsDao
import xyz.luko.server.data.database.dao.UserDao
import xyz.luko.server.data.database.table.CharacterFsrsStateTable
import xyz.luko.server.data.database.table.UserTable
import xyz.luko.server.domain.mapper.DomainMapping.toRow

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

    suspend fun getUser(id: String): MeDto?
    suspend fun getUserId(id: String): EntityID<Int>?
}


// --- Implementation ---

internal class DefaultUserRepository(
    private val dao: UserDao,
    private val characterFsrsDao: CharacterFsrsDao,
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

    override suspend fun getUser(id: String): MeDto? {
        val user = dao.getStreak(id) ?: return null
        val levels = characterFsrsDao.getLevels(user[UserTable.id])
        return MeDto(
            currentStreak = user[UserTable.streak],
            levels = levels.groupBy(
                { it[CharacterFsrsStateTable.level] },
                { it[CharacterFsrsStateTable.characterCode] }
            )
        )
    }

    override suspend fun getUserId(id: String): EntityID<Int>? {
        return dao.getByFID(id)?.get(UserTable.id)
    }
}
