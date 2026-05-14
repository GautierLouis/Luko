package xyz.luko.network.interfaces

import xyz.luko.apicontracts.dto.UserJson

interface UserService {
    suspend fun me(): Result<UserJson>
}
