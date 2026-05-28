package xyz.luko.desktoApp

import xyz.luko.baseui.preview.PreviewProvider
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.ResponseList
import xyz.luko.domain.repository.DictionaryRepository

class FakeDictionaryRepository : DictionaryRepository {
    override suspend fun createSession(
        level: List<CharacterFrequencyLevel>,
        limit: Int
    ) = Result.success(PreviewProvider.dictionaryList)

    override suspend fun getByLevel(
        level: CharacterFrequencyLevel,
        page: Int,
        limit: Int
    ) = Result.success(
        ResponseList(
            true,
            PreviewProvider.simpleDictionaryList
        )
    )

    override suspend fun search(
        levels: List<CharacterFrequencyLevel>,
        query: String,
        page: Int,
        limit: Int
    ) = Result.success(
        ResponseList(
            true,
            PreviewProvider.simpleDictionaryList
        )
    )

    override suspend fun getByName(code: Int) = Result.success(PreviewProvider.dictionary)
}
