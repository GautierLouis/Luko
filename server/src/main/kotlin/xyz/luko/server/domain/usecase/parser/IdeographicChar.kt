package xyz.luko.server.domain.usecase.parser


enum class IdeographicChar(val symbol: Int, val codepoint: Int, val arity: Int) {
    /*'⿰'*/ LEFT_TO_RIGHT(12272, 0x2FF0, 2),
    /*'⿱'*/ ABOVE_TO_BELOW(12273, 0x2FF1, 2),
    /*'⿲'*/ LEFT_TO_MIDDLE_AND_RIGHT(12274, 0x2FF2, 3),
    /*'⿳'*/ ABOVE_TO_MIDDLE_AND_BELOW(12275, 0x2FF3, 3),
    /*'⿴'*/ SURROUND(12276, 0x2FF4, 2),
    /*'⿵'*/ SURROUND_FROM_ABOVE(12277, 0x2FF5, 2),
    /*'⿶'*/ SURROUND_FROM_BELOW(12278, 0x2FF6, 2),
    /*'⿷'*/ SURROUND_FROM_LEFT(12279, 0x2FF7, 2),
    /*'⿸'*/ SURROUND_FROM_UPPER_LEFT(12280, 0x2FF8, 2),
    /*'⿹'*/ SURROUND_FROM_UPPER_RIGHT(12281, 0x2FF9, 2),
    /*'⿺'*/ SURROUND_FROM_LOWER_LEFT(12282, 0x2FFA, 2),
    /*'⿻'*/ OVERLAID(12283, 0x2FFB, 2);

    companion object {
        private val byCode = entries.associateBy { it.codepoint }

        fun fromCodepoint(cp: Int): IdeographicChar? = byCode[cp]
    }
}
