package xyz.luko.server.data.database.dao

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.jdbc.update
import org.jetbrains.exposed.v1.jdbc.upsert
import xyz.luko.server.data.database.StatementMapping.add
import xyz.luko.server.data.database.StatementMapping.update
import xyz.luko.server.data.database.table.UserTable
import xyz.luko.server.domain.mapper.ResultRowMapping.toDto
import xyz.luko.server.domain.model.UpdateUserRow
import xyz.luko.server.domain.model.UserRow

interface UserDao {
    suspend fun insertUser(user: UserRow)
    suspend fun updateUser(user: UpdateUserRow)
    suspend fun getById(id: String): UserRow?
    suspend fun getByFcm(fcm: String): UserRow?
}

// --- Implementation ---

internal class DefaultUserDao : UserDao {
    override suspend fun insertUser(user: UserRow) {
        suspendTransaction { UserTable.upsert { it.add(user) } }
    }

    override suspend fun updateUser(user: UpdateUserRow) {
        suspendTransaction {
            UserTable.update(
                where = { UserTable.firebaseUid eq user.id }
            ) { statements -> statements.update(user) }
        }
    }

    override suspend fun getById(id: String): UserRow? =
        suspendTransaction {
            UserTable.selectAll()
                .where { UserTable.firebaseUid eq id }
                .limit(1)
                .firstOrNull()?.toDto()
        }

    override suspend fun getByFcm(fcm: String): UserRow? =
        suspendTransaction {
            UserTable.selectAll()
                .where { UserTable.fcmToken eq fcm }
                .limit(1)
                .firstOrNull()?.toDto()
        }
}
