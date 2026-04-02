package com.louisgautier.domain.usecase

import com.louisgautier.utils.nowInUtc
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlinx.datetime.minus


internal class ComputeDayStreak {

    fun calculateCurrentDayStreak(sortedDates: List<LocalDate>): Int {
        if (sortedDates.isEmpty()) return 0

        val today = nowInUtc()

        // Check if the most recent session is today or yesterday
        val daysBetween = today.daysUntil(sortedDates.first())
        if (daysBetween <= 0) return 0

        var streak = 0
        var expectedDate = today

        for (date in sortedDates) {
            if (date == expectedDate || date == expectedDate.minus(1, DateTimeUnit.DAY)) {
                streak++
                expectedDate = date.minus(1, DateTimeUnit.DAY)
            } else {
                break
            }
        }

        return streak
    }

    fun calculateLongestDayStreak(dates: List<String>): Int {
        if (dates.isEmpty()) return 0

        val sortedDates = dates.map { LocalDate.parse(it) }.sorted()

        var longestStreak = 1
        var currentStreak = 1

        for (i in 1 until sortedDates.size) {
            val previousDate = sortedDates[i - 1]
            val currentDate = sortedDates[i]

            val daysDifference = previousDate.daysUntil(currentDate)

            when (daysDifference) {
                0 -> continue // Same day, multiple sessions
                1 -> {
                    currentStreak++
                    longestStreak = maxOf(longestStreak, currentStreak)
                }

                else -> currentStreak = 1 // Streak broken
            }
        }

        return longestStreak
    }

}