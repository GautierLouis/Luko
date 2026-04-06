package com.louisgautier.feature

import com.louisgautier.dictionary.dictionaryModule
import com.louisgautier.feed.feedModule
import com.louisgautier.home.homeModule
import com.louisgautier.learning.learningModule
import com.louisgautier.login.loginModule
import com.louisgautier.profile.profileModule
import org.koin.dsl.module

val featureModule = module {
    includes(
        homeModule,
        feedModule,
        loginModule,
        profileModule,
        learningModule,
        dictionaryModule,
    )
}