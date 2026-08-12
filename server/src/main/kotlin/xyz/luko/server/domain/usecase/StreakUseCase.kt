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

    suspend fun updateStreak(id: EntityID<Int>): Pair<Boolean, Int> {
        val user = userDao.getByID(id) ?: return false to 0

        val today = Clock.System.now().toLocalDateTime(TimeZone.UTC).date

        val currentStreak = user[UserTable.streak]

        val lastReview = user[UserTable.streakUpdatedAt]?.let {
            Instant.fromEpochSeconds(it).toLocalDateTime(TimeZone.UTC).date
        }

        val (newStreak, increased) = when (lastReview) {
            null -> 1 to true                           // first ever review
            today -> currentStreak to false              // already counted today, no-op
            today.minus(1, DateTimeUnit.DAY) -> {       // consecutive day, bump
                (currentStreak + 1) to true
            }

            else -> 1 to false                            // gap > 1 day, streak broken
        }

        if (lastReview != today) {
            userDao.updateStreak(
                id,
                newStreak,
                Clock.System.now().toEpochMilliseconds()
            )
        }

        return increased to newStreak
    }
}
