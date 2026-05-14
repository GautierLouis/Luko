package xyz.luko.network

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.java.Java

actual val engineFactory: HttpClientEngineFactory<*> = Java
