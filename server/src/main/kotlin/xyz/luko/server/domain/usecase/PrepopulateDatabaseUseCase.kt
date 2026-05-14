package xyz.luko.server.domain.usecase

import co.touchlab.kermit.Logger
import xyz.luko.server.domain.repo.DictionaryRepository
import xyz.luko.server.domain.repo.FileRepository
import xyz.luko.server.domain.repo.GraphicRepository

class PrepopulateDatabaseUseCase(
    private val dictionaryRepository: DictionaryRepository,
    private val graphicRepository: GraphicRepository,
    private val fileRepository: FileRepository
) {
    suspend fun init() {
        try {
            val needsDictionary = !dictionaryRepository.exist()
            val needsGraphic = !graphicRepository.exist()

            if (!needsDictionary && !needsGraphic) {
                Logger.i(
                    tag = "DATABASE INIT",
                    messageString = "Database already populated, skipping."
                )
                return
            }

            if (needsDictionary) {
                val data = fileRepository.loadDictionaryFile()
                dictionaryRepository.batchCreate(data)
            }
            if (needsGraphic) {
                val data2 = fileRepository.loadGraphicFile()
                graphicRepository.batchCreate(data2)
            }

        } catch (e: Exception) {
            Logger.e(
                tag = "DATABASE INIT",
                throwable = e,
                messageString = e.message ?: "Error while parsing files",
            )
        }
    }
}