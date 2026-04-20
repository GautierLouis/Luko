package com.louisgautier.server.database

import com.louisgautier.server.ServerConfig
import com.louisgautier.server.database.entity.DictionaryTable
import com.louisgautier.server.database.entity.GraphicTable
import com.louisgautier.server.database.entity.UserTable
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.Database as DatabaseFactory

class Database(
    private val config: ServerConfig
) {
    fun init() {
        val dataSource = buildDataSource()
        connect(dataSource)
        migrate()
    }

    private fun connect(dataSource: HikariDataSource) {
        DatabaseFactory.connect(dataSource)
    }

    private fun migrate() {
        transaction {
            addLogger(config.sqlLogger)
            SchemaUtils.create(DictionaryTable, GraphicTable, UserTable)
        }
    }

    private fun buildDataSource(): HikariDataSource {
        return HikariDataSource().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl =
                "jdbc:postgresql://${config.databaseHost}:${config.databasePort}/${config.databaseName}"
            username = config.databaseUser
            password = config.databasePassword
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            addDataSourceProperty("ssl", "true")
            addDataSourceProperty("sslmode", "require")
            addDataSourceProperty("reWriteBatchedInserts", "true")
            validate()
        }
    }
}