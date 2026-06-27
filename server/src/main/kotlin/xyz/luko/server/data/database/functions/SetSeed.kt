package xyz.luko.server.data.database.functions

import org.jetbrains.exposed.v1.core.BooleanColumnType
import org.jetbrains.exposed.v1.core.CustomFunction
import org.jetbrains.exposed.v1.core.doubleParam

@Suppress("unused")
class SetSeed(seed: Double) : CustomFunction<Boolean>(
    functionName = "setseed",
    columnType = BooleanColumnType(),
    expr = arrayOf(doubleParam(seed))
)
