package com.louisgautier.server.domain.repo.implem

/**
 * Expands a plain pinyin query into all its tone accent variants
 * e.g. :
 * "shi" -> ["shi", "shī", "shí", "shǐ", "shì"]
 * */
fun String.toneVariants(): List<String> {
    val variants = mutableListOf(this)
    val toneMap = mapOf(
        'a' to listOf("ā", "á", "ǎ", "à"),
        'e' to listOf("ē", "é", "ě", "è"),
        'i' to listOf("ī", "í", "ǐ", "ì"),
        'o' to listOf("ō", "ó", "ǒ", "ò"),
        'u' to listOf("ū", "ú", "ǔ", "ù"),
    )
    toneMap.forEach { (plain, accented) ->
        if (contains(plain)) {
            accented.forEach {
                variants.add(replace(plain.toString(), it))
            }
        }
    }
    return variants
}