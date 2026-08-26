package xyz.luko.learning.congratulation.streak

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import xyz.luko.domain.repository.SessionRepository
import kotlin.time.Clock

internal class GetWeekStreakUseCase(
    private val sessionRepository: SessionRepository,
) {
    suspend operator fun invoke(): List<DayStreak> {
        val today = Clock.System.now()
            .toLocalDateTime(TimeZone.UTC)
            .date

        val monday = today.minus(today.dayOfWeek.isoDayNumber - 1, DateTimeUnit.DAY)
        val week = (0..6).map { monday.plus(it, DateTimeUnit.DAY) }

        val hasSessionFlags = sessionRepository.hasSessionFor(week)

        return week.zip(hasSessionFlags) { date, hasSession ->
            DayStreak(date = date, hasSession = hasSession, isToday = date == today)
        }
    }
}
