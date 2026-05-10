package com.louisgautier.server.mock

import com.louisgautier.server.database.Database
import com.louisgautier.server.database.entity.DictionaryTable
import com.louisgautier.server.database.entity.GraphicTable
import com.louisgautier.server.database.entity.UserTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

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