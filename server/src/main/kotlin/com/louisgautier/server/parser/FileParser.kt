package com.louisgautier.server.parser

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevelDto
import com.louisgautier.apicontracts.dto.Decomposition
import com.louisgautier.apicontracts.dto.Dictionary
import com.louisgautier.apicontracts.dto.Etymology
import com.louisgautier.apicontracts.dto.GraphicDto
import com.louisgautier.apicontracts.dto.Point
import com.louisgautier.apicontracts.dto.Stroke
import com.louisgautier.server.database.entity.DictionaryDao
import com.louisgautier.server.database.entity.GraphicDao
import com.louisgautier.server.domain.DictionaryRepository
import com.louisgautier.server.domain.GraphicRepository
import com.louisgautier.server.domain.suspendTransaction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.toListOf
import org.jetbrains.kotlinx.dataframe.io.readCsv
import java.io.InputStream

class FileParser(
    private val dictionaryRepository: DictionaryRepository,
    private val graphicRepository: GraphicRepository,
) {
    companion object {
        private const val DICTIONARY_FILE: String = "dictionary.txt"
        private const val GRAPHIC_FILE: String = "graphics.txt"
        private const val HANZI_FILE: String = "hanzi.csv"
        private const val EMPTY_COUNT = 0L
    }

    suspend fun init() {
        try {
            val (dictionaryEntryCount, graphicCount) = getDatabaseCounts()

            if (dictionaryEntryCount == EMPTY_COUNT) {
                val hanzi = parseHanzi()
                val dict = parseDictionary().map {
                    val level = hanzi.find { h -> h.first == it.character }?.second
                    Dictionary(
                        code = it.character.toString().codePointAt(0),
                        definition = it.definition,
                        pinyin = it.pinyin,
                        decomposition = it.decomposition,
                        decompositionList = decompose(it.decomposition),
                        level = rankToLevel(level),
                        etymology = it.etymology,
                        radical = it.radical,
                        matches = it.matches,
                    )
                }
                dictionaryRepository.batchCreate(dict)
            }
            if (graphicCount == EMPTY_COUNT) {
                val graph = parseGraphic().map { g ->
                    GraphicDto(
                        g.character.toString().codePointAt(0),
                        g.strokes,
                        g.medians.map { m -> Stroke(points = m.map { p -> Point(p[0], p[1]) }) }
                    )
                }
                graphicRepository.batchCreate(graph)
            }
        } catch (e: Exception) {
            println("An error occurred during data initialization: ${e.message}")
        }
    }

    private suspend fun getDatabaseCounts(): Pair<Long, Long> = suspendTransaction {
        Pair(DictionaryDao.count(), GraphicDao.count())
    }

    private fun parseDictionary(): List<DictionaryParsed> {
        return parseNdjsonLines(path = DICTIONARY_FILE)
    }

    private fun parseGraphic(): List<GraphicParser> {
        return parseNdjsonLines(path = GRAPHIC_FILE)
    }

    private fun parseHanzi(): List<Pair<Char, Int?>> {
        return try {
            val csvStream = getResourceAsStream(HANZI_FILE)
            val df = DataFrame.readCsv(csvStream)
            val hanzi: List<CsvRow> = df.toListOf()
            hanzi.filter { it.simplified != null }
                .map { it.simplified!!.first() to it.rank_rsh }
        } catch (t: Throwable) {
            System.err.println("Failed: ${t.message}")
            t.printStackTrace()
            emptyList()
        }
    }

    private fun rankToLevel(rank: Int?): CharacterFrequencyLevelDto = when {
        rank == null -> CharacterFrequencyLevelDto.UNKNOWN
        rank <= 500 -> CharacterFrequencyLevelDto.COMMON
        rank <= 1500 -> CharacterFrequencyLevelDto.FREQUENT
        rank <= 3500 -> CharacterFrequencyLevelDto.STANDARD
        rank <= 7000 -> CharacterFrequencyLevelDto.EXTENDED
        rank <= 9000 -> CharacterFrequencyLevelDto.RARE
        else -> CharacterFrequencyLevelDto.OBSOLETE
    }

    private fun decompose(original: String): List<Decomposition> {
        return try {
            val parser = IdeographicParser(original)
            val tree = parser.parse()
            decompositionOrderedJsonFromIds(tree)
        } catch (_: Throwable) {
            emptyList()
        }
    }

    private fun decompositionOrderedJsonFromIds(
        node: IdeographicNode,
    ): List<Decomposition> {
        val decompositionMap = linkedMapOf<Int, MutableList<Int>>()

        fun walk(n: IdeographicNode) {
            when (n) {
                is IdeographicNode.Glyph -> { /* nothing to emit at glyph nodes */
                }

                is IdeographicNode.Operator -> {
                    // For children, if child is Glyph -> child char, if Operator -> operator symbol
                    val childrenStrings = n.children.map { child ->
                        when (child) {
                            is IdeographicNode.Glyph -> child.code
                            is IdeographicNode.Operator -> child.op.symbol
                        }
                    }

                    val list = decompositionMap.getOrPut(n.op.symbol) { mutableListOf() }
                    list.addAll(childrenStrings)

                    // then recurse into children so later operators are processed in preorder
                    n.children.forEach { walk(it) }
                }
            }
        }

        walk(node)

        return decompositionMap.map { Decomposition(it.key, it.value) }
    }

    private fun serializeOriginal(node: IdeographicNode): String {
        return when (node) {
            is IdeographicNode.Glyph -> node.code.toString()
            is IdeographicNode.Operator -> node.op.symbol.toString() + node.children.joinToString(
                separator = ""
            ) {
                serializeOriginal(it)
            }
        }
    }

    private inline fun <reified T> parseNdjsonLines(
        path: String,
        json: Json = Json { ignoreUnknownKeys = false }
    ): List<T> {
        val stream = getResourceAsStream(path)
        val text = stream.bufferedReader(Charsets.UTF_8).use { it.readText() }
        return text.lineSequence()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { json.decodeFromString<T>(it) }
            .toList()
    }

    private fun getResourceAsStream(fileName: String): InputStream {
        return object {}.javaClass.classLoader.getResourceAsStream(fileName)
            ?: throw IllegalStateException("Resource not found: $fileName")
    }
}

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

@Serializable
data class DictionaryParsed(
    val character: Char,
    val definition: String = "",
    val pinyin: List<String> = emptyList(),
    val decomposition: String = "",
    val etymology: Etymology? = null,
    val radical: String? = null,
    val matches: List<List<Int>?> = emptyList()
)

@Serializable
data class GraphicParser(
    val character: Char,
    val strokes: List<String>,
    val medians: List<List<List<Float>>>
)