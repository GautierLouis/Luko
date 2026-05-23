package xyz.luko.server.domain.model.source

import kotlinx.serialization.Serializable

@Serializable
data class HskTranscriptionsSource(
    val pinyin: String? = null,
    val numeric: String? = null,
    val wadegiles: String? = null,
    val bopomofo: String? = null,
    val romatzyh: String? = null
)

@Serializable
data class HskFormSource(
    val traditional: String? = null,
    val transcriptions: HskTranscriptionsSource? = null,
    val meanings: List<String> = emptyList(),
    val classifiers: List<String> = emptyList()
)

@Serializable
data class HskEntrySource(
    val simplified: String,
    val radical: String? = null,
    val level: List<String> = emptyList(),
    val frequency: Int? = null,
    val pos: List<String> = emptyList(),
    val forms: List<HskFormSource> = emptyList()
)
