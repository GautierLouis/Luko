package xyz.luko.server.domain.usecase

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import xyz.luko.server.data.database.dao.UserDao
import xyz.luko.server.data.database.table.UserTable
import kotlin.time.Clock
import kotlin.time.Instant

class StreakUseCase(
    private val userDao: UserDao
) {

    suspend fun updateStreak(id: EntityID<Int>): StreakUpdater {
        val user = userDao.getByID(id) ?: return StreakUpdater(false, 0, false)

        val today = Clock.System.now().toLocalDateTime(TimeZone.UTC).date

        val currentStreak = user[UserTable.streak]

        val lastReview = user[UserTable.streakUpdatedAt]?.let {
            Instant.fromEpochSeconds(it).toLocalDateTime(TimeZone.UTC).date
        }

        val (newStreak, increased, shouldWrite) = when (lastReview) {
            null -> Triple(1, true, true)
            today -> Triple(currentStreak, false, false)
            today.minus(1, DateTimeUnit.DAY) -> Triple(currentStreak + 1, true, true)
            else -> Triple(1, false, true)
        }

        return StreakUpdater(
            shouldUpdate = shouldWrite,
            newStreak = newStreak,
            hasIncrease = increased
        )
    }
}

data class StreakUpdater(
    val shouldUpdate: Boolean,
    val newStreak: Int,
    val hasIncrease: Boolean,
)
