package xyz.luko.server.data.database.table

import org.jetbrains.exposed.dao.id.IntIdTable

object HanziTable : IntIdTable("hanzi") {
    val code = integer("code").index()
    val traditional = varchar("traditional", 8).nullable()
    val pinyin = varchar("pinyin", 64).nullable()
    val pinyinStyle2 = varchar("pinyin_style2", 64).nullable()
    val zhuyinBopomofo = varchar("zhuyin_bopomofo", 64).nullable()
    val jyupting = varchar("jyupting", 32).nullable()
    val decomposition1 = varchar("decomposition1", 64).nullable()
    val decomposition2WithRadical = varchar("decomposition2_with_radical", 64).nullable()
    val meaningDecomposition2WithRadical = text("meaning_decomposition2_with_radical").nullable()
    val decomposition3Graphical = varchar("decomposition3_graphical", 64).nullable()
    val componentIn = text("component_in").nullable()
    val exampleWords = text("example_words").nullable()
    val meaningJunda = text("meaning_junda").nullable()
    val keyWordRsh = varchar("key_word_rsh", 64).nullable()
    val hsk30Level = varchar("hsk30_level", 16).nullable()
    val rankRsh = integer("rank_rsh").nullable()
    val frequencyJunda = integer("frequency_junda").nullable()
    val indexGscc = varchar("index_gscc", 16).nullable()
    val learningOrderCcm = integer("learning_order_ccm").nullable()
    val ccCedictDefinitions = text("cc_cedict_definitions").nullable()
}
