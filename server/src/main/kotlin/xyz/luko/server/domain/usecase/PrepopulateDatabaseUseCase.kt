package xyz.luko.server.domain.usecase

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import xyz.luko.server.data.database.dao.DictionaryDao
import xyz.luko.server.data.database.dao.PrepopulateDao
import xyz.luko.server.data.storage.StorageSource
import xyz.luko.server.domain.mapper.DomainMapping.toRow

class PrepopulateDatabaseUseCase(
    private val source: StorageSource,
    private val dao: PrepopulateDao,
    private val dictionaryDao: DictionaryDao,
    private val compositionUseCase: CompositionUseCase,
    private val strokeRenderingUseCase: StrokeRenderingUseCase,
    private val pinyinToAudioUseCase: PinyinToAudioUseCase
) {

    suspend fun init() = coroutineScope {

        // Get files and insert them into DB first
        val anyImported = awaitAll(
            async {
                if (!dao.isGraphicInitialized()) {
                    importGraphics(); true
                } else false
            },
            async {
                if (!dao.isCharacterInitialized()) {
                    importDictionary(); true
                } else false
            },
            async {
                if (!dao.isHanziInitialized()) {
                    importHanzi(); true
                } else false
            },
            async {
                if (!dao.isVocabularyInitialized()) {
                    importVocabulary(); true
                } else false
            }
        ).any { it }

        if (!anyImported) return@coroutineScope

        // Build custom data
        dao.getPrepopulateData()
            .map {
                it.toRow(
                    composition = compositionUseCase.decompose(it.decomposition),
                    medians = strokeRenderingUseCase.execute(it.medians),
                    sound = it.pinyin.mapNotNull { p -> pinyinToAudioUseCase.execute(p) }
                )
            }.run { dictionaryDao.batchInsert(this) }
    }

    private suspend fun importGraphics() = source.loadGraphicFile()
        .run { dao.insertGraphicFile(this.toRow()) }

    private suspend fun importDictionary() = source.loadDictionaryFile()
        .run { dao.insertCharacterFile(this.toRow()) }

    private suspend fun importHanzi() = source.loadHanziFile()
        .run { dao.insertHanziFile(this.toRow()) }

    private suspend fun importVocabulary() = source.loadVocabulary()
        .run { dao.insertVocabulary(this.toRow()) }
}
