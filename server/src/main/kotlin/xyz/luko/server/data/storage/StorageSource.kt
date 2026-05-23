package xyz.luko.server.data.storage

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.serialization.json.Json
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.toListOf
import org.jetbrains.kotlinx.dataframe.io.readCsv
import xyz.luko.server.domain.model.source.CharacterSource
import xyz.luko.server.domain.model.source.GraphicSource
import xyz.luko.server.domain.model.source.HanziSource
import xyz.luko.server.domain.model.source.HskEntrySource

interface StorageSource {
    suspend fun loadGraphicFile(): List<GraphicSource>
    suspend fun loadDictionaryFile(): List<CharacterSource>
    suspend fun loadHanziFile(): List<HanziSource>
    suspend fun loadVocabulary(): List<HskEntrySource>
}

// --- Implementation ---

internal class DefaultStorageSource(
    private val supabase: SupabaseClient
) : StorageSource {

    companion object {
        private const val BUCKET_NAME = "hanzi-files"
        private const val DICTIONARY_FILE: String = "dictionary.txt" //Character
        private const val GRAPHIC_FILE: String = "graphics.txt" // Character strokes
        private const val HANZI_FILE: String = "hanzi.csv" // Alternative Character (old)
        private const val WORDS_FILE: String = "words-complete.json" // All Chinese words
    }

    override suspend fun loadGraphicFile(): List<GraphicSource> {
        val file = loadFile(GRAPHIC_FILE)
        file.lineSequence()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { Json.decodeFromString<GraphicSource>(it) }
            .toList()
        return parseNDJSON(file)
    }

    override suspend fun loadDictionaryFile(): List<CharacterSource> {
        val file = loadFile(DICTIONARY_FILE)
        return parseNDJSON(file)
    }

    override suspend fun loadHanziFile(): List<HanziSource> {
        val bytes = loadBytes(HANZI_FILE)
        val df = DataFrame.readCsv(bytes.inputStream())
        return df.toListOf()
    }

    override suspend fun loadVocabulary(): List<HskEntrySource> {
        val file = loadFile(WORDS_FILE)
        return Json.decodeFromString(file)
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

    private inline fun <reified T> parseNDJSON(
        file: String,
        json: Json = Json.Default
    ): List<T> {
        return file.lineSequence()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { json.decodeFromString<T>(it) }
            .toList()
    }
}
