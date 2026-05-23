package xyz.luko.server.domain.model.source

import kotlinx.serialization.Serializable

@Suppress("PropertyName")
@Serializable
data class HanziSource(
    val this_table: Int,
    val simplified: String,
    val traditional: String? = null,
    val pinyin: String? = null,
    val pinyin_style2: String? = null,
    val zhuyin_bopomofo: String? = null,
    val jyupting: String? = null,
    val decomposition1: String? = null,
    val decomposition2_with_radical: String? = null,
    val meaning_decomposition2_with_radical: String? = null,
    val decomposition3_graphical: String? = null,
    val component_in: String? = null,
    val example_words: String? = null,
    val meaning_junda: String? = null,
    val key_word_rsh: String? = null,
    val hsk30_level: String? = null,
    val rank_rsh: Int? = null,
    val frequency_junda: Int? = null,
    val index_gscc: String? = null,
    val learning_order_ccm: Int? = null,
    val cc_cedict_definitions: String? = null
)
