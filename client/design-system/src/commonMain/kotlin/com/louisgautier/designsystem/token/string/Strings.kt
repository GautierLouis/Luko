package com.louisgautier.designsystem.token.string

import androidx.compose.runtime.Immutable

@Immutable
data class Strings(
    val greeting: String,

    //Home
    val practice: String,
    val goodMorning: String,
    val goodAfternoon: String,
    val goodEvening: String,
    val welcomeBack: String,

    //BottomBar
    val home: String,
    val dictionary: String,
    val profile: String,

    //Statistic
    val streak: String,
    val sessions: String,
    val totalScore: String,
    val questionCount: String,
    val time: String,
    val score: String,
    val difficulty: String,
    val overallStatistics: String,

    //Difficulty
    val easy: String,
    val medium: String,
    val hard: String,


    //Content Description
    val actionSearch: String,
    val actionBack: String,
    val actionFilter: String,
    val actionClear: String
)

