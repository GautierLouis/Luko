package xyz.luko.server.data.database.table

object TableList {
    fun get() = listOf(
        HanziTable,
        HskEntryTable,
        HskFormTable,
        HskEntryCharacterTable,
        HskEntryLevelTable,
        DictionaryTable,
        CharacterTable,
        GraphicTable,
        UserTable,
    ).toTypedArray()
}
