package xyz.luko.apicontracts

import kotlinx.serialization.json.Json

val defaultJson =
    Json {
        prettyPrint = false
        isLenient = true
        ignoreUnknownKeys = true
        allowStructuredMapKeys = true
        encodeDefaults = true
    }
