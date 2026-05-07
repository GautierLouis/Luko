package com.louisgautier.server.domain.repo

import com.louisgautier.apicontracts.dto.DictionaryDto
import com.louisgautier.apicontracts.dto.GraphicDto

interface FileRepository {
    suspend fun loadGraphicFile(): List<GraphicDto>
    suspend fun loadDictionaryFile(): List<DictionaryDto>
}