package xyz.luko.desktoApp

import androidx.paging.PagingData
import kotlinx.coroutines.flow.emptyFlow
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.SessionResponse
import xyz.luko.domain.model.Statistics
import xyz.luko.domain.repository.SessionRepository

class FakeSessionRepo : SessionRepository {
    override suspend fun save(session: Session, responses: List<SessionResponse>) {}
    override fun getLastSessions(limit: Int) = emptyFlow<List<Session>>()
    override fun getSessions() = emptyFlow<PagingData<Session>>()
    override suspend fun getLastSessionsFor(code: Int) = emptyList<Session>()
    override fun getStatistics() = emptyFlow<Statistics>()
}
