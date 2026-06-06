package xyz.luko.server.domain.model

data class DictionaryRow(
    val code: Int,
    val char: String,
    val decomposition: String,
    val medians: String,
    val frequency: Int,
    val hskLevel: Int,
    val sound: String,
)
