package xyz.luko.network.interfaces


/**
 * Interface for accessing and managing user authentication tokens between :data:network and :core:firebase
 *
 */
interface TokenProvider {
    suspend fun getToken(forceRefresh: Boolean = false): String?
}
