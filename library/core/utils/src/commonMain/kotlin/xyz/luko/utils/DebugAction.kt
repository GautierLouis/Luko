package xyz.luko.utils

/**
 * Provide a convenience interface to mock flow of screens without breaking module architecture
 */
interface DebugAction {
    val label: String
    suspend fun execute()
}
