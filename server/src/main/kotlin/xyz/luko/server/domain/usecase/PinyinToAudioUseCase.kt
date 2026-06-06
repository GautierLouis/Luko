package xyz.luko.server.domain.usecase

class PinyinToAudioUseCase {

    fun execute(pinyin: String?): String? {
        if (pinyin.isNullOrEmpty()) return null

        val toneMap = mapOf(
            // 1st tone (ā ē ī ō ū ǖ)
            'ā' to ('a' to 1), 'ē' to ('e' to 1), 'ī' to ('i' to 1),
            'ō' to ('o' to 1), 'ū' to ('u' to 1), 'ǖ' to ('ü' to 1),
            // 2nd tone (á é í ó ú ǘ)
            'á' to ('a' to 2), 'é' to ('e' to 2), 'í' to ('i' to 2),
            'ó' to ('o' to 2), 'ú' to ('u' to 2), 'ǘ' to ('ü' to 2),
            // 3rd tone (ǎ ě ǐ ǒ ǔ ǚ)
            'ǎ' to ('a' to 3), 'ě' to ('e' to 3), 'ǐ' to ('i' to 3),
            'ǒ' to ('o' to 3), 'ǔ' to ('u' to 3), 'ǚ' to ('ü' to 3),
            // 4th tone (à è ì ò ù ǜ)
            'à' to ('a' to 4), 'è' to ('e' to 4), 'ì' to ('i' to 4),
            'ò' to ('o' to 4), 'ù' to ('u' to 4), 'ǜ' to ('ü' to 4),
        )

        var toneNumber = 0
        val stripped = buildString {
            for (char in pinyin) {
                val mapped = toneMap[char]
                if (mapped != null) {
                    append(mapped.first)  // base letter
                    toneNumber = mapped.second
                } else {
                    append(char)
                }
            }
        }

        val name = if (toneNumber > 0) "$stripped$toneNumber" else stripped
        return "$name.mp3"
    }
}
