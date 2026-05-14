package xyz.luko.server.database.source

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.serialization.json.Json
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.toListOf
import org.jetbrains.kotlinx.dataframe.io.readCsv
import xyz.luko.server.domain.usecase.parser.CsvRow
import xyz.luko.server.domain.usecase.parser.DictionaryParsed
import xyz.luko.server.domain.usecase.parser.GraphicParser

class DefaultFileDataSource(
    private val supabase: SupabaseClient
) : FileDataSource {

    companion object {
        private const val BUCKET_NAME = "hanzi-files"
        private const val DICTIONARY_FILE: String = "dictionary.txt"
        private const val GRAPHIC_FILE: String = "graphics.txt"
        private const val HANZI_FILE: String = "hanzi.csv"
    }

    override suspend fun loadGraphicFile(): List<GraphicParser> {
        val file = loadFile(GRAPHIC_FILE)
        return parseFile(file)
    }

    override suspend fun loadDictionaryFile(): List<DictionaryParsed> {
        val file = loadFile(DICTIONARY_FILE)
        return parseFile(file)
    }

    override suspend fun loadHanziFile(): List<CsvRow> {
        val bytes = loadBytes(HANZI_FILE)
        val df = DataFrame.readCsv(bytes.inputStream())
        return df.toListOf()
    }

    private suspend fun loadFile(fileName: String): String {
        return loadBytes(fileName).decodeToString()
    }

    private suspend fun loadBytes(fileName: String): ByteArray {
        return runCatching {
            supabase.storage.from(BUCKET_NAME).downloadAuthenticated(fileName)
        }.getOrElse {
            throw IllegalStateException("Failed to load '$fileName' from bucket '$BUCKET_NAME'", it)
        }
    }

    private inline fun <reified T> parseFile(
        file: String,
        json: Json = Json { ignoreUnknownKeys = false }
    ): List<T> {
        return file.lineSequence()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { json.decodeFromString<T>(it) }
            .toList()
    }
}