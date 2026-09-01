package xyz.luko.server.data.database.dao

import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.jdbc.update
import org.jetbrains.exposed.v1.jdbc.upsert
import xyz.luko.server.data.database.StatementMapping.add
import xyz.luko.server.data.database.StatementMapping.update
import xyz.luko.server.data.database.table.UserTable
import xyz.luko.server.domain.model.UpdateUserRow
import xyz.luko.server.domain.model.UserRow

interface UserDao {
    suspend fun insertUser(user: UserRow)
    suspend fun updateFcm(user: UpdateUserRow)
    suspend fun getByFID(id: String): ResultRow?
    suspend fun getByID(id: EntityID<Int>): ResultRow?
    suspend fun getStreak(id: String): ResultRow?
    suspend fun getByFcm(fcm: String): ResultRow?
    suspend fun updateStreak(
        id: EntityID<Int>,
        streak: Int,
        streakUpdatedAt: Long,
    )
}

// --- Implementation ---

internal class DefaultUserDao : UserDao {
    override suspend fun insertUser(user: UserRow) {
        suspendTransaction { UserTable.upsert { it.add(user) } }
    }

    override suspend fun updateFcm(user: UpdateUserRow) {
        suspendTransaction {
            UserTable.update(
                where = { UserTable.firebaseUid eq user.id }
            ) { statements -> statements.update(user) }
        }
    }

    override suspend fun getByFID(id: String): ResultRow? =
        suspendTransaction {
            UserTable.selectAll()
                .where { UserTable.firebaseUid eq id }
                .limit(1)
                .firstOrNull()
        }

    override suspend fun getByFcm(fcm: String): ResultRow? =
        suspendTransaction {
            UserTable.selectAll()
                .where { UserTable.fcmToken eq fcm }
                .limit(1)
                .firstOrNull()
        }

    override suspend fun getByID(id: EntityID<Int>): ResultRow? =
        suspendTransaction {
            UserTable.selectAll()
                .where { UserTable.id eq id }
                .limit(1)
                .firstOrNull()
        }

    /**
     * Warning: This method should be called in a transaction.
     */
    override suspend fun updateStreak(
        id: EntityID<Int>,
        streak: Int,
        streakUpdatedAt: Long,
    ) {
        UserTable.update(
            where = { UserTable.id eq id }
        ) {
            it[UserTable.streak] = streak
            it[UserTable.streakUpdatedAt] = streakUpdatedAt
        }
    }

    override suspend fun getStreak(id: String): ResultRow? = suspendTransaction {
        UserTable
            .select(UserTable.id, UserTable.streak)
            .where { UserTable.firebaseUid eq id }
            .firstOrNull() ?: return@suspendTransaction null
    }
}
