package com.louisgautier.domain.repository

import com.louisgautier.domain.mapper.LevelCount
import com.louisgautier.domain.model.CharacterFrequencyLevel
import com.louisgautier.domain.model.DictionaryWithGraphic
import com.louisgautier.domain.model.Graphic
import com.louisgautier.domain.model.ResponseList
import com.louisgautier.domain.model.SimpleDictionary

interface CharacterRepository {
    suspend fun getLevelCount(): Result<List<LevelCount>>
    suspend fun generateSession(
        level: List<CharacterFrequencyLevel>,
        limit: Int
    ): Result<List<DictionaryWithGraphic>>

    suspend fun getByLevel(
        level: CharacterFrequencyLevel,
        page: Int,
        limit: Int
    ): Result<ResponseList<SimpleDictionary>>

    suspend fun search(
        levels: List<CharacterFrequencyLevel>,
        query: String,
        page: Int,
        limit: Int
    ): Result<ResponseList<SimpleDictionary>>

    suspend fun getByName(code: Int): Result<DictionaryWithGraphic>
    suspend fun getSVG(code: Int): Result<Graphic>
}

