package xyz.luko.server.domain.model


data class HanziRow(
    val thisTable: Int,
    val code: Int,
    val traditional: String? = null,
    val pinyin: String? = null,
    val pinyinStyle2: String? = null,
    val zhuyinBopomofo: String? = null,
    val jyupting: String? = null,
    val decomposition1: String? = null,
    val decomposition2WithRadical: String? = null,
    val meaningDecomposition2WithRadical: String? = null,
    val decomposition3Graphical: String? = null,
    val componentIn: String? = null,
    val exampleWords: String? = null,
    val meaningJunda: String? = null,
    val keyWordRsh: String? = null,
    val hsk30Level: String? = null,
    val rankRsh: Int? = null,
    val frequencyJunda: Int? = null,
    val indexGscc: String? = null,
    val learningOrderCcm: Int? = null,
    val ccCedictDefinitions: String? = null,
)
