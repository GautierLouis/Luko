package xyz.luko.server.mock

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import xyz.luko.server.database.Database
import xyz.luko.server.database.entity.DictionaryTable
import xyz.luko.server.database.entity.GraphicTable
import xyz.luko.server.database.entity.UserTable

class FakeDatabase : Database {
    override fun init() {
        org.jetbrains.exposed.sql.Database.connect(
            url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
            driver = "org.h2.Driver",
            user = "sa",
            password = "",
        )

        transaction {
            SchemaUtils.create(DictionaryTable, GraphicTable, UserTable)
        }
    }
}