package xyz.luko.server.data.database.functions

import org.jetbrains.exposed.sql.BooleanColumnType
import org.jetbrains.exposed.sql.CustomFunction
import org.jetbrains.exposed.sql.doubleParam

@Suppress("unused")
class SetSeed(seed: Double) : CustomFunction<Boolean>(
    functionName = "setseed",
    columnType = BooleanColumnType(),
    expr = arrayOf(doubleParam(seed))
)
