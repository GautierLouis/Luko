package xyz.luko.server.data.database

import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import xyz.luko.server.ServerConfig
import xyz.luko.server.data.database.table.TableList
import xyz.luko.server.domain.usecase.PrepopulateDatabaseUseCase
import org.jetbrains.exposed.v1.jdbc.Database as ExposedDatabase

class DefaultDatabase(
    private val config: ServerConfig,
    private val prepopulate: PrepopulateDatabaseUseCase
) : Database {
    override suspend fun init() {
        val dataSource = buildDataSource()
        migrate(dataSource)
        connect(dataSource)
        prepopulate.init()
    }

    private fun migrate(dataSource: HikariDataSource) {
        Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/migration")
            .baselineVersion("0")
            .baselineOnMigrate(true)
            .load()
            .migrate()
    }

    private fun connect(dataSource: HikariDataSource) {
        ExposedDatabase.connect(dataSource)
        transaction {
            addLogger(config.sqlLogger)
            TableList.get().forEach { table ->
                println(table.ddl.joinToString(";\n"))
            }
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
