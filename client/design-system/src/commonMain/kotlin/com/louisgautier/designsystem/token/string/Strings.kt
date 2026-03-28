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

    //Content Description
    val actionSearch: String,
    val actionBack: String,
    val actionFilter: String,
)

