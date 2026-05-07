package com.louisgautier.designsystem.token.string

import androidx.compose.runtime.Immutable

@Immutable
data class Strings(
    val greeting: String,
    // Home
    val practice: String,
    val goodMorning: String,
    val goodAfternoon: String,
    val goodEvening: String,
    val welcomeBack: String,
    // BottomBar
    val home: String,
    val dictionary: String,
    val feed: String,
    val profile: String,
    // Statistic
    val streak: String,
    val sessions: String,
    val totalScore: String,
    val questions: String,
    val time: String,
    val score: String,
    val difficulty: String,
    val overallStatistics: String,
    // Difficulty
    val easy: String,
    val medium: String,
    val hard: String,
    val easyTitle: String,
    val easyCaption: String,
    val mediumTitle: String,
    val mediumCaption: String,
    val hardTitle: String,
    val hardCaption: String,
    // HSK Level
    val common: String,
    val frequent: String,
    val standard: String,
    val commonCaption: String,
    val frequentCaption: String,
    val standardCaption: String,
    // Learning
    val newSession: String,
    val next: String,
    val previous: String,
    val start: String,
    val builderPickQuestionCount: String,
    val builderSelectDifficulty: String,
    val sessionHeaderTitle: String,
    val sessionComplete: String,
    val sessionFinish: String,
    val sessionQuit: String,
    val leaveDialogTitle: String,
    val leaveDialogMessage: String,
    val leaveDialogConfirmation: String,
    val leaveDialogDismiss: String,
    val congratulationTitle: String,
    val congratulationMessage: String,
    val congratulationButtonRestart: String,
    val congratulationButtonHome: String,
    // Dictionary
    val searchPlaceholder: String,
    val filterFrequencyGroup: String,
    // Date
    val today: String,
    val yesterday: String,
    val lastWeek: String,
    val lastMonth: String,
    // Error
    val error: String,
    val retry: String,
    // Content Description
    val actionSearch: String,
    val actionBack: String,
    val actionFilter: String,
    val actionClear: String,
    val actionReset: String,
    // Formatter
    val datePattern: String,
)
