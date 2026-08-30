package xyz.luko.learning.session

import xyz.luko.domain.model.SessionSettings
import xyz.luko.domain.model.TemporaryResponse
import xyz.luko.domain.model.TemporarySession
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.domain.repository.UserRepository
import xyz.luko.domain.usecase.SyncSessionUseCase
import xyz.luko.learning.congratulation.EndOfSessionCoordinator
import xyz.luko.ui.navigation.AppNavigation

internal class EndOfSessionUseCase(
    private val sessionRepository: SessionRepository,
    private val syncSessionUseCase: SyncSessionUseCase,
    private val userRepository: UserRepository,
    private val coordinator: EndOfSessionCoordinator,
) {
    suspend fun prepare(
        session: TemporarySession,
        responses: List<TemporaryResponse>,
        settings: SessionSettings,
    ) {
        // Save first, no matter what
        val sessionId = sessionRepository.save(session, responses)

        sessionRepository.setLastSessionConfiguration(
            configuration = SessionSettings(
                difficultyLevel = settings.difficultyLevel,
                count = settings.count,
                frequencyLevel = settings.frequencyLevel,
            )
        )

        // then call API
        userRepository.reviewSession(session, responses)
            .onSuccess { result ->
                //Save updated session + responese + levels
                val updatedSession = syncSessionUseCase.execute(
                    session.copy(id = sessionId),
                    result.sessionResponse,
                    result.levels
                )

                // Navigate to Reward flow
                coordinator.prepareAndStart(result, updatedSession)
            }
            .onFailure {
                //TODO Navigate with error
                AppNavigation.navigateHome()
            }
    }
}
