package xyz.luko.domain.repository

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.Clock

class UpdateStreakUseCase(
    private val userRepository: UserRepository,
) {

    suspend fun maybeUpdate(): StreakResult {
        val now = Clock.System.now()
        val todayUtc = now.toLocalDateTime(TimeZone.UTC).date
        val currentTimezone = TimeZone.currentSystemDefault()

        val prefs = userRepository.getStreak()

        // Parse last updated date in the timezone it was saved with
        val lastDate = prefs?.lastUpdatedDate?.let {
            LocalDate.parse(it)
        }

        val alreadyUpdatedToday = lastDate == todayUtc

        if (alreadyUpdatedToday) {
            return StreakResult(
                streakCount = prefs.streakCount,
                updatedToday = true
            )
        }

        // Check if streak should continue or reset
        val yesterdayUtc = todayUtc.minus(1, DateTimeUnit.DAY)
        val isConsecutive = lastDate == yesterdayUtc

        val newCount = if (prefs == null || !isConsecutive) {
            1 // first ever, or streak broken
        } else {
            prefs.streakCount + 1
        }

        userRepository.updateStreak(
            StreakPreferences(
                streakCount = newCount,
                lastUpdatedDate = todayUtc.toString(),
                lastUpdatedTimezone = currentTimezone.id
            )
        )

        return StreakResult(
            streakCount = newCount,
            updatedToday = false // just updated now, was false before this call
        )
    }
}

@Serializable
data class StreakPreferences(
    val streakCount: Int,           // current streak days
    val lastUpdatedDate: String,    // ISO date in UTC: "2026-07-01"
    val lastUpdatedTimezone: String // e.g. "Asia/Kuala_Lumpur"
)

data class StreakResult(
    val streakCount: Int,
    val updatedToday: Boolean
)
