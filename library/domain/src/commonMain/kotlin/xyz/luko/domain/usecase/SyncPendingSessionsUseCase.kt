package xyz.luko.domain.usecase

import xyz.luko.domain.repository.SessionRepository
import xyz.luko.domain.repository.UserRepository

class SyncPendingSessionsUseCase(
    private val sessionRepository: SessionRepository,
    private val userRepository: UserRepository,
    private val syncSessionUseCase: SyncSessionUseCase,
) {

    suspend fun execute() {
        sessionRepository.getUnsyncedSessions().forEach { session ->
            val responses = sessionRepository.getUnsyncedResponses(session.id)
            userRepository.reviewSession(session, responses)
                .onSuccess { result ->
                    syncSessionUseCase.execute(
                        session,
                        result.sessionResponse,
                        result.levels
                    )
                }
        }
    }
}
