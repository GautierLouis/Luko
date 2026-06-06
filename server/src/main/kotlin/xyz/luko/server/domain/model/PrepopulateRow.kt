package xyz.luko.server.domain.model


data class PrepopulateRow(
    val code: Int,
    val medians: List<List<List<Float>>>,
    val decomposition: String,
    val frequency: Int? = null,
    val hskLevel: Int,
    val pinyin: List<String>
)
