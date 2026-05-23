package xyz.luko.server.mock

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import xyz.luko.server.data.database.Database
import xyz.luko.server.data.database.table.CharacterTable
import xyz.luko.server.data.database.table.GraphicTable
import xyz.luko.server.data.database.table.UserTable

class FakeDatabase : Database {
    override suspend fun init() {
        org.jetbrains.exposed.sql.Database.connect(
            url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
            driver = "org.h2.Driver",
            user = "sa",
            password = "",
        )

        transaction {
            SchemaUtils.create(CharacterTable, GraphicTable, UserTable)
        }
    }
}
