package xyz.luko.network.interfaces

import xyz.luko.apicontracts.dto.UserDto

interface UserService {
    suspend fun me(): Result<UserDto>
}
