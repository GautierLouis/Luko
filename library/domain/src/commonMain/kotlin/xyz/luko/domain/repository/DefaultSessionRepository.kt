package xyz.luko.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import xyz.luko.database.dao.SessionDao
import xyz.luko.database.dao.SessionResponseDao
import xyz.luko.domain.mapper.SessionMapper.toDto
import xyz.luko.domain.mapper.SessionMapper.toEntity
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.SessionResponse
import xyz.luko.domain.model.Statistics
import xyz.luko.domain.usecase.ComputeDayStreak
import xyz.luko.domain.usecase.ComputeDifficulty
import xyz.luko.utils.toUTCDate
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal class DefaultSessionRepository(
    private val sessionDao: SessionDao,
    private val responseDao: SessionResponseDao
) : SessionRepository {
    val dayStreakComputer = ComputeDayStreak()
    val difficultyComputer = ComputeDifficulty()

    override suspend fun save(
        session: Session,
        responses: List<SessionResponse>,
    ) {
        val sessionEntity = session.toEntity()
        val responseEntity = responses.map { it.toEntity() }
        sessionDao.insertSessionWithResponses(sessionEntity, responseEntity)
    }

    override fun getLastSessions(limit: Int): Flow<List<Session>> =
        sessionDao.getLast(limit).map { list -> list.map { it.toDto() } }

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
            val dates =
                basic.uniqueDates
                    ?.map { it.toUTCDate() }
                    .orEmpty()
            Statistics(
                totalScore = basic.totalScore,
                averageTime = basic.averageTime.toDuration(DurationUnit.SECONDS),
                averageDifficulty = difficultyComputer.average(basic.difficulties.orEmpty()),
                currentDayStreak = dayStreakComputer.calculateCurrentDayStreak(dates),
                sessionCount = basic.sessionCount,
            )
        }
    }

    override suspend fun getEndOfSessionStats(): EndOfSessionStats {
        val lastSession = sessionDao.getLastSession()!!

        val allDates = sessionDao.getUniqueDates().map { it.toUTCDate() }

        val oldStreak = allDates
            .filter { it != lastSession.date.toUTCDate() }
            .let { dayStreakComputer.calculateCurrentDayStreak(it) }

        val newStreak = dayStreakComputer.calculateCurrentDayStreak(allDates)

        return EndOfSessionStats(lastSession.toDto(), oldStreak, newStreak)
    }
}

data class EndOfSessionStats(
    val lastSession: Session,
    val oldStreak: Int,
    val newStreak: Int
)
