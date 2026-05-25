package xyz.luko.server.data.database

import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import xyz.luko.server.ServerConfig
import xyz.luko.server.data.database.table.TableList
import xyz.luko.server.domain.usecase.PrepopulateDatabaseUseCase

class DefaultDatabase(
    private val config: ServerConfig,
    private val prepopulate: PrepopulateDatabaseUseCase
) : Database {
    override suspend fun init() {
        val dataSource = buildDataSource()
        connect(dataSource)
        migrate()
        prepopulate.init()
    }

    private fun connect(dataSource: HikariDataSource) {
        org.jetbrains.exposed.sql.Database.connect(dataSource)
    }

    private fun migrate() {
        transaction {
            addLogger(config.sqlLogger)
            SchemaUtils.create(*TableList.get())
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
