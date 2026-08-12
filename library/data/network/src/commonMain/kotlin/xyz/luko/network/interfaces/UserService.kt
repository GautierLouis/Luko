package xyz.luko.network.interfaces

import xyz.luko.apicontracts.dto.ReviewAttemptRequest
import xyz.luko.apicontracts.dto.ReviewResultDto
import xyz.luko.apicontracts.dto.UserDto

interface UserService {
    suspend fun me(): Result<UserDto>

    suspend fun reviewSession(review: ReviewAttemptRequest): Result<List<ReviewResultDto>>
}
