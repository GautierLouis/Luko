package com.louisgautier.domain.repository

import com.louisgautier.domain.model.SessionResponse
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.model.Statistics

interface SessionRepository {
    suspend fun save(session: Session, responses: List<SessionResponse>)
    suspend fun getLastSessions(limit: Int): List<Session>
    suspend fun getLastSessionsFor(code: Int): List<Session>
    suspend fun getStatistics(): Statistics

}

