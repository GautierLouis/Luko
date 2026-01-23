package com.louisgautier.server.database

import com.louisgautier.server.BuildEnvironment
import com.louisgautier.server.database.entity.DictionaryTable
import com.louisgautier.server.database.entity.GraphicTable
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.inject


fun Application.configureDatabase() {

    val buildEnvironment: BuildEnvironment by inject()

    val config = HikariDataSource().apply {
        driverClassName = "org.postgresql.Driver"
        jdbcUrl = "jdbc:postgresql://${buildEnvironment.databaseHost}:${buildEnvironment.databasePort}/${buildEnvironment.databaseName}"
        username = buildEnvironment.databaseUser
        password = buildEnvironment.databasePassword
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        addDataSourceProperty("ssl", "true")
        addDataSourceProperty("sslmode", "require")
        addDataSourceProperty("reWriteBatchedInserts", "true")
        validate()
    }

    Database.connect(config)

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(DictionaryTable, GraphicTable)
    }
}