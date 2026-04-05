package com.louisgautier.composeApp

import com.louisgautier.utils.AppConfig

fun interface AppConfigBuilder {
    operator fun invoke(): AppConfig
}