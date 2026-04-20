package com.louisgautier.server

import io.github.jan.supabase.logging.LogLevel
import io.ktor.server.application.ApplicationEnvironment
import org.jetbrains.exposed.sql.Slf4jSqlDebugLogger
import org.jetbrains.exposed.sql.SqlLogger
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.koin.core.logger.Level

data class ServerConfig(
    val port: Int,
    val databaseHost: String,
    val databaseName: String,
    val databasePort: String,
    val databaseUser: String,
    val databasePassword: String,
    val supabaseUrl: String,
    val supabasePublicKey: String,
    val supabasePrivateKey: String,
    val supabaseJwtKey: String,
    val supabaseIssuer: String,
    val supabaseJwks: String,
    val supabaseAudience: String,
    val env: Env
) {
    enum class Env {
        DEV, PROD
    }

    // One source of truth for all log levels
    private val isDev get() = env == Env.DEV
    val koinLogLevel: Level get() = if (isDev) Level.DEBUG else Level.INFO
    val ktorLogLevel: LogLevel get() = if (isDev) LogLevel.DEBUG else LogLevel.INFO
    val sqlLogger: SqlLogger get() = if (isDev) Slf4jSqlDebugLogger else StdOutSqlLogger


    companion object {
        fun build(environment: ApplicationEnvironment) = with(environment.config) {
            ServerConfig(
                port = property("ktor.deployment.port").getString().toInt(),
                databaseHost = property("database.host").getString(),
                databaseName = property("database.name").getString(),
                databasePort = property("database.port").getString(),
                databaseUser = property("database.user").getString(),
                databasePassword = property("database.password").getString(),
                supabaseUrl = property("supabase.url").getString(),
                supabasePublicKey = property("supabase.publicKey").getString(),
                supabasePrivateKey = property("supabase.privateKey").getString(),
                supabaseJwtKey = property("supabase.jwtKey").getString(),
                supabaseIssuer = property("supabase.issuer").getString(),
                supabaseJwks = property("supabase.jwks").getString(),
                supabaseAudience = property("supabase.audience").getString(),
                env = Env.valueOf(property("app.env").getString())
            )
        }
    }
}