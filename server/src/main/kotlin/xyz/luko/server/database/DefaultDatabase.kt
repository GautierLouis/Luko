package xyz.luko.server.database

import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import xyz.luko.server.ServerConfig
import xyz.luko.server.database.entity.DictionaryTable
import xyz.luko.server.database.entity.GraphicTable
import xyz.luko.server.database.entity.UserTable
import xyz.luko.server.domain.usecase.PrepopulateDatabaseUseCase

class DefaultDatabase(
    private val config: ServerConfig,
    private val prepopulate: PrepopulateDatabaseUseCase
) : Database {
    override fun init() {
        val dataSource = buildDataSource()
        connect(dataSource)
        migrate()
        prepopulate()
    }

    private fun connect(dataSource: HikariDataSource) {
        org.jetbrains.exposed.sql.Database.connect(dataSource)
    }

    private fun migrate() {
        transaction {
            addLogger(config.sqlLogger)
            SchemaUtils.create(DictionaryTable, GraphicTable, UserTable)
        }
    }

    private fun prepopulate() {
        CoroutineScope(Dispatchers.IO).launch {
            prepopulate.init()
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