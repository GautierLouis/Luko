package xyz.luko.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import xyz.luko.apicontracts.dto.CharacterFrequencyLevelDto
import xyz.luko.apicontracts.dto.DictionaryDto
import xyz.luko.apicontracts.dto.ResponseListDto
import xyz.luko.apicontracts.dto.SimpleDictionaryDto
import xyz.luko.apicontracts.routing.Destination
import xyz.luko.network.interfaces.CharacterService

class DefaultCharacterService(
    private val client: HttpClient,
) : CharacterService {

    override suspend fun generateSession(
        level: List<CharacterFrequencyLevelDto>,
        limit: Int,
    ): Result<List<DictionaryDto>> =
        call {
            client.get(Destination.GenerateSession(levels = level, limit = limit))
        }

    override suspend fun getByLevel(
        level: CharacterFrequencyLevelDto,
        page: Int,
        limit: Int,
    ): Result<ResponseListDto<SimpleDictionaryDto>> =
        call {
            client.get(Destination.Characters.ByLevel(page = page, limit = limit, level = level))
        }

    override suspend fun getByName(code: Int): Result<DictionaryDto> =
        call { client.get(Destination.Characters.ByName(code = code)) }

    override suspend fun search(
        levels: List<CharacterFrequencyLevelDto>,
        query: String,
        page: Int,
        limit: Int,
    ): Result<ResponseListDto<SimpleDictionaryDto>> =
        call {
            client.get(
                Destination.Characters.Search(
                    levels = levels,
                    query = query,
                    page = page,
                    limit = limit,
                ),
            )
        }
}
