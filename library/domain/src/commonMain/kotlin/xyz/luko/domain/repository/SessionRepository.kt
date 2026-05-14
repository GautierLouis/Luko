package xyz.luko.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.SessionResponse
import xyz.luko.domain.model.Statistics

interface SessionRepository {
    suspend fun save(
        session: Session,
        responses: List<SessionResponse>,
    )

    suspend fun getLastSessions(limit: Int): List<Session>

    fun getSessions(): Flow<PagingData<Session>>

    suspend fun getLastSessionsFor(code: Int): List<Session>

    suspend fun getStatistics(): Statistics
}
