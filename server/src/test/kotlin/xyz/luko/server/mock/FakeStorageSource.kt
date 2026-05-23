package xyz.luko.server.mock

import xyz.luko.server.data.storage.StorageSource
import xyz.luko.server.domain.model.source.CharacterSource
import xyz.luko.server.domain.model.source.GraphicSource
import xyz.luko.server.domain.model.source.HanziSource
import xyz.luko.server.domain.model.source.HskEntrySource

class FakeStorageSource : StorageSource {
    override suspend fun loadGraphicFile(): List<GraphicSource> {
        return emptyList()
    }

    override suspend fun loadDictionaryFile(): List<CharacterSource> {
        return emptyList()
    }

    override suspend fun loadHanziFile(): List<HanziSource> {
        return emptyList()
    }

    override suspend fun loadVocabulary(): List<HskEntrySource> {
        return emptyList()
    }
}
