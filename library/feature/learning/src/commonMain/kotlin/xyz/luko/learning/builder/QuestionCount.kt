package xyz.luko.learning.builder

enum class QuestionCount(
    val value: Int,
) {
    FIVE(5),
    TEN(10),
    FIFTEEN(15),
    TWENTY(20);

    fun shifted(by: Int): QuestionCount {
        val entries = QuestionCount.entries
        val newIndex = (entries.indexOf(this) + by).coerceIn(entries.indices)
        return entries[newIndex]
    }
}
