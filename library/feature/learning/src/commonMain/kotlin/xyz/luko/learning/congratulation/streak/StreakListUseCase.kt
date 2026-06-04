package xyz.luko.learning.congratulation.streak

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import xyz.luko.learning.congratulation.streak.ui.SegmentPosition
import xyz.luko.learning.congratulation.streak.ui.StreakDay
import kotlin.time.Clock

internal class StreakListUseCase {

    fun build(
        completedDates: List<LocalDate>,
    ): ImmutableList<StreakDay> {

        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val monday = today.minus(today.dayOfWeek.ordinal, DateTimeUnit.DAY)

        val completedSet = completedDates.toSet()

        val animatedDate = completedDates.maxOrNull()

        val currentStreakDates = buildSet {

            var cursor = animatedDate ?: return@buildSet

            add(cursor)

            while (cursor.minus(1, DateTimeUnit.DAY) in completedSet) {
                cursor = cursor.minus(1, DateTimeUnit.DAY)
                add(cursor)
            }
        }

        val days = (0..6).map { offset ->

            val date = monday.plus(offset, DateTimeUnit.DAY)

            val completed = date in completedSet

            val prevCompleted = date.minus(1, DateTimeUnit.DAY) in completedSet

            val nextCompleted = date.plus(1, DateTimeUnit.DAY) in completedSet

            val segment = when {
                !completed -> SegmentPosition.None

                !prevCompleted && !nextCompleted -> when (date.dayOfWeek) {
                    DayOfWeek.MONDAY -> SegmentPosition.Last
                    DayOfWeek.SUNDAY -> SegmentPosition.First
                    else -> SegmentPosition.Single
                }

                !prevCompleted && nextCompleted -> SegmentPosition.First

                prevCompleted && !nextCompleted -> SegmentPosition.Last

                prevCompleted && nextCompleted -> SegmentPosition.Middle

                else -> SegmentPosition.None
            }

            StreakDay(
                date = date,
                segment = segment,
                shouldAnimate = animatedDate == date,
                currentStreak = date in currentStreakDates
            )
        }

        return days.toImmutableList()
    }
}
