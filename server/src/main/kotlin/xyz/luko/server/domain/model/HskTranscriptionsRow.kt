package xyz.luko.server.domain.model

data class HskTranscriptionsRow(
    val pinyin: String? = null,
    val numeric: String? = null,
    val wadegiles: String? = null,
    val bopomofo: String? = null,
    val romatzyh: String? = null
)

data class HskFormRow(
    val traditional: String? = null,
    val transcriptions: HskTranscriptionsRow? = null,
    val meanings: String? = null,
    val classifiers: String? = null
)

data class HskEntryRow(
    val simplified: String,
    val radical: String? = null,
    val level: String,
    val frequency: Int? = null,
    val pos: String? = null,
    val forms: List<HskFormRow> = emptyList()
)
