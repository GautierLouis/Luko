package com.louisgautier.server

import io.ktor.server.application.ApplicationEnvironment

data class BuildEnvironment(
    //val type: BuildEnvironmentType,
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
    val environment: BuildEnvironmentType
) {
    enum class BuildEnvironmentType {
        DEV, PROD
    }

    companion object {
        fun build(environment: ApplicationEnvironment) = with(environment.config) {
            BuildEnvironment(
                //type = BuildEnvironmentType.valueOf(property("app.env").getString()),
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
                environment = BuildEnvironmentType.valueOf(property("app.env").getString())
            )
        }
    }
}