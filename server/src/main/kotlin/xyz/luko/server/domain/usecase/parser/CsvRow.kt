package xyz.luko.server.domain.usecase.parser

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CsvRow(
    @SerialName("this_table") val this_table: Int,
    @SerialName("simplified") val simplified: String? = null,
    @SerialName("traditional") val traditional: String? = null,
    @SerialName("pinyin") val pinyin: String? = null,
    @SerialName("pinyin_style2") val pinyin_style2: String? = null,
    @SerialName("zhuyin_bopomofo") val zhuyin_bopomofo: String? = null,
    @SerialName("jyupting") val jyupting: String? = null,
    @SerialName("decomposition1") val decomposition1: String? = null,
    @SerialName("decomposition2_with_radical") val decomposition2_with_radical: String? = null,
    @SerialName("meaning_decomposition2_with_radical") val meaning_decomposition2_with_radical: String? = null,
    @SerialName("decomposition3_graphical") val decomposition3_graphical: String? = null,
    @SerialName("component_in") val component_in: String? = null,
    @SerialName("example_words") val example_words: String? = null,
    @SerialName("meaning_junda") val meaning_junda: String? = null,
    @SerialName("key_word_rsh") val key_word_rsh: String? = null,
    @SerialName("hsk30_level") val hsk30_level: String? = null,
    @SerialName("rank_rsh") val rank_rsh: Int? = null,
    @SerialName("frequency_junda") val frequency_junda: Int? = null,
    @SerialName("index_gscc") val index_gscc: String? = null,
    @SerialName("learning_order_ccm") val learning_order_ccm: Int? = null,
    @SerialName("cc_cedict_definitions") val cc_cedict_definitions: String? = null
)