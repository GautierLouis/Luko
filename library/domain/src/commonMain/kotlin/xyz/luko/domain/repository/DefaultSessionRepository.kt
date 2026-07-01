package xyz.luko.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import xyz.luko.database.dao.SessionDao
import xyz.luko.database.dao.SessionResponseDao
import xyz.luko.domain.mapper.SessionMapper.toDto
import xyz.luko.domain.mapper.SessionMapper.toEntity
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.SessionResponse
import xyz.luko.domain.model.Statistics
import xyz.luko.domain.usecase.ComputeDifficulty
import kotlin.time.DurationUnit
import kotlin.time.Instant
import kotlin.time.toDuration

internal class DefaultSessionRepository(
    private val sessionDao: SessionDao,
    private val responseDao: SessionResponseDao,
) : SessionRepository {
    val difficultyComputer = ComputeDifficulty()

    override suspend fun save(
        session: Session,
        responses: List<SessionResponse>,
    ): Long {
        val sessionEntity = session.toEntity()
        val responseEntity = responses.map { it.toEntity() }
        return sessionDao.insertSessionWithResponses(sessionEntity, responseEntity)
    }

    override fun getLastSessions(limit: Int): Flow<List<Session>> =
        sessionDao.getLast(limit).map { list -> list.map { it.toDto() } }

    override suspend fun getLastSession(): Session {
        return sessionDao.getLastSession()?.toDto()!!
    }

    override suspend fun getLastSessions(): List<Session> =
        sessionDao.getAll().map { it.toDto() }

    override suspend fun getSession(id: Long): Session =
        sessionDao.get(id).toDto()

    override suspend fun getResponses(sessionId: Long): List<SessionResponse> =
        responseDao.get(sessionId).map { it.toDto() }

    override suspend fun getSimilarResponse(code: Int): List<SessionResponse> =
        responseDao.getSimilar(code).map { it.toDto() }

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

    override fun getStatistics(): Flow<Statistics> {
        return sessionDao.getBasicStatistics().map { basic ->
            Statistics(
                averageTime = basic.averageTime.toDuration(DurationUnit.SECONDS),
                averageDifficulty = difficultyComputer.average(basic.difficulties.orEmpty()),
                sessionCount = basic.sessionCount,
                averageQuestionsCount = basic.averageQuestionsCount,
                averageAccuracy = basic.averageAccuracy,
            )
        }
    }

    override suspend fun getSessionDatesForWeek(start: Instant, end: Instant): List<LocalDate> =
        sessionDao.getSessionDatesForWeek(start.toString(), end.toString())
            .map { Instant.parse(it).toLocalDateTime(TimeZone.currentSystemDefault()).date }
}
