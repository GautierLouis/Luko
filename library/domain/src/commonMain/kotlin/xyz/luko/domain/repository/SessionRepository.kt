package xyz.luko.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.SessionResponse
import xyz.luko.domain.model.Statistics
import kotlin.time.Instant

interface SessionRepository {
    suspend fun save(
        session: Session,
        responses: List<SessionResponse>,
    ): Long

    fun getLastSessions(limit: Int): Flow<List<Session>>

    suspend fun getLastSession(): Session

    suspend fun getLastSessions(): List<Session>

    suspend fun getSession(id: Long): Session

    suspend fun getResponses(sessionId: Long): List<SessionResponse>

    suspend fun getSimilarResponse(code: Int): List<SessionResponse>

    fun getSessions(): Flow<PagingData<Session>>

    suspend fun getLastSessionsFor(code: Int): List<Session>

    fun getStatistics(): Flow<Statistics>


    suspend fun getSessionDatesForWeek(start: Instant, end: Instant): List<LocalDate>
}
