package xyz.luko.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import xyz.luko.database.dao.SessionDao
import xyz.luko.database.dao.SessionResponseDao
import xyz.luko.domain.mapper.SessionMapper.toDto
import xyz.luko.domain.mapper.SessionMapper.toEntity
import xyz.luko.domain.mapper.toDomain
import xyz.luko.domain.mapper.toDto
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.Dictionary
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.SessionResponse
import xyz.luko.domain.model.SessionSettings
import xyz.luko.domain.model.TemporaryResponse
import xyz.luko.domain.model.TemporarySession
import xyz.luko.network.interfaces.CharacterService
import xyz.luko.preferences.AppPreferences
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Instant

interface SessionRepository {
    suspend fun createSession(
        level: List<CharacterFrequencyLevel>,
        limit: Int,
    ): Result<List<Dictionary>>

    suspend fun save(
        session: TemporarySession,
        responses: List<TemporaryResponse>,
    ): Long

    suspend fun getLastSessions(limit: Int = Int.MAX_VALUE): List<Session>
    fun observeSessions(limit: Int = Int.MAX_VALUE): Flow<List<Session>>

    suspend fun getSession(id: Long): Session

    suspend fun getResponses(sessionId: Long): List<SessionResponse>

    suspend fun getSimilarResponse(code: Int): List<SessionResponse>

    fun getSessions(): Flow<PagingData<Session>>

    suspend fun getLastSessionsFor(code: Int): List<Session>

    suspend fun hasSessionFor(days: List<LocalDate>): List<Boolean>

    suspend fun setLastSessionConfiguration(configuration: SessionSettings)
    fun getLastSessionConfiguration(): Flow<List<SessionSettings>>

    suspend fun getUnsyncedSessions(): List<TemporarySession>
    suspend fun getUnsyncedResponses(code: Long): List<TemporaryResponse>
}


internal class DefaultSessionRepository(
    private val sessionDao: SessionDao,
    private val responseDao: SessionResponseDao,
    private val appPreferences: AppPreferences,
    private val characterService: CharacterService,
) : SessionRepository {

    override suspend fun createSession(
        level: List<CharacterFrequencyLevel>,
        limit: Int,
    ) = characterService
        .createSession(level.map { it.toDto() }, limit)
        .mapCatching { response ->
            response.data
                .ifEmpty { throw IllegalStateException("No items from server") }
                .map { dto -> dto.toDomain() }
        }


    override suspend fun save(
        session: TemporarySession,
        responses: List<TemporaryResponse>,
    ): Long {
        val sessionEntity = session.toEntity()
        val responseEntity = responses.map { it.toEntity() }
        return sessionDao.insertSessionWithResponses(sessionEntity, responseEntity)
    }

    override suspend fun getLastSessions(limit: Int): List<Session> =
        sessionDao.getLastSessions(limit).map { it.toDto() }

    override fun observeSessions(limit: Int): Flow<List<Session>> =
        sessionDao.getLast(limit).map { list -> list.map { it.toDto() } }

    override suspend fun getSession(id: Long): Session =
        sessionDao.get(id).toDto()

    override suspend fun getResponses(sessionId: Long): List<SessionResponse> =
        responseDao.get(sessionId).map { it.toDto() }

    override suspend fun getSimilarResponse(code: Int): List<SessionResponse> =
        responseDao.getSimilar(code).map { it.toDto() }

    //TODO: VM should handle this, not repo
    override fun getSessions(): Flow<PagingData<Session>> =
        Pager(
            config =
                PagingConfig(
                    pageSize = 20,
                    prefetchDistance = 5,
                    enablePlaceholders = false,
                ),
            pagingSourceFactory = { sessionDao.getAllPaged() },
        ).flow.map { pagingData ->
            pagingData.map { it.toDto() }
        }

    override suspend fun getLastSessionsFor(code: Int): List<Session> =
        sessionDao.getLastFor(code).map { it.toDto() }

    override suspend fun hasSessionFor(days: List<LocalDate>): List<Boolean> {
        val instantStr = days.map { it.toString() }
        val session = sessionDao.hasSessionFor(instantStr).toSet()
        return instantStr.map { it in session }
    }

    override fun getLastSessionConfiguration(): Flow<List<SessionSettings>> =
        appPreferences.observeSessionConfiguration().map { set ->
            set?.map { Json.decodeFromString<SessionSettings>(it) }?.take(2) ?: emptyList()
        }

    override suspend fun setLastSessionConfiguration(configuration: SessionSettings) {
        val last = appPreferences.getSessionConfiguration()
            ?.map { Json.decodeFromString<SessionSettings>(it) }
            ?: emptySet()

        val updated = (last + configuration)
            .distinct()
            .take(2)
            .map { Json.encodeToString(it) }
            .toSet()
        appPreferences.updateSessionConfiguration(updated)
    }

    override suspend fun getUnsyncedSessions(): List<TemporarySession> {
        return sessionDao.getUnsyncedSessions().map {
            TemporarySession(
                id = it.id,
                date = Instant.parse(it.date),
                duration = it.duration.milliseconds,
                difficulty = DifficultyLevel.valueOf(it.difficulty),
                questionsCount = it.questionsCount,
            )
        }
    }

    override suspend fun getUnsyncedResponses(code: Long): List<TemporaryResponse> {
        return sessionDao.getResponsesForSession(code).map {
            val r = Json.decodeFromString<SessionResponse>(it.response)
            TemporaryResponse(
                code = r.code,
                pinyin = r.pinyin,
                strokes = r.strokes,
                references = r.references,
                recognitionResult = r.recognitionResult,
                difficultyLevel = r.difficultyLevel,
            )
        }
    }
}
