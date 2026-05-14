package xyz.luko.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.luko.database.dao.SessionDao
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
    private val dao: SessionDao,
) : SessionRepository {
    val dayStreakComputer = ComputeDayStreak()
    val difficultyComputer = ComputeDifficulty()

    override suspend fun save(
        session: Session,
        responses: List<SessionResponse>,
    ) {
        val sessionEntity = session.toEntity()
        val responseEntity = responses.map { it.toEntity() }
        dao.insertSessionWithResponses(sessionEntity, responseEntity)
    }

    override suspend fun getLastSessions(limit: Int): List<Session> =
        dao.getLast(limit).map { it.toDto() }

    override fun getSessions(): Flow<PagingData<Session>> =
        Pager(
            config =
                PagingConfig(
                    pageSize = 20,
                    prefetchDistance = 5,
                    enablePlaceholders = false,
                ),
            pagingSourceFactory = { dao.getAllPaged() },
        ).flow.map { pagingData ->
            pagingData.map { it.toDto() }
        }

    override suspend fun getLastSessionsFor(code: Int): List<Session> =
        dao.getLastFor(code).map { it.toDto() }

    override suspend fun getStatistics(): Statistics {
        val basic = dao.getBasicStatistics()
        val dates =
            basic.uniqueDates
                ?.map { it.toUTCDate() }
                .orEmpty()
        return Statistics(
            totalScore = basic.totalScore,
            averageTime = basic.averageTime.toDuration(DurationUnit.SECONDS),
            averageDifficulty = difficultyComputer.average(basic.difficulties.orEmpty()),
            currentDayStreak = dayStreakComputer.calculateCurrentDayStreak(dates),
            sessionCount = basic.sessionCount,
        )
    }
}
