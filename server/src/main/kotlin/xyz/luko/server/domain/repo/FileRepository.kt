package xyz.luko.server.domain.repo

import xyz.luko.apicontracts.dto.DictionaryDto
import xyz.luko.apicontracts.dto.GraphicDto
import java.awt.Font

interface FileRepository {
    suspend fun loadGraphicFile(): List<GraphicDto>
    suspend fun loadDictionaryFile(): List<DictionaryDto>
    suspend fun loadFont(): Font
}
