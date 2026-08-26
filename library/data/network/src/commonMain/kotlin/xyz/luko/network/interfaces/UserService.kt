package xyz.luko.network.interfaces

import xyz.luko.apicontracts.dto.MeDto
import xyz.luko.apicontracts.dto.ReviewAttemptRequest
import xyz.luko.apicontracts.dto.ReviewResultDto

interface UserService {
    suspend fun me(): Result<MeDto>

    suspend fun reviewSession(review: ReviewAttemptRequest): Result<ReviewResultDto>
}
