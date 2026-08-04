package xyz.luko.server.domain.usecase

import xyz.luko.apicontracts.dto.IdeographicCharDto
import xyz.luko.apicontracts.dto.IdeographicNodeDto

class DecompositionParseException(message: String) : Exception(message)

fun IdeographicNodeDto.containsUnknownGlyph(): Boolean = when (this) {
    is IdeographicNodeDto.Unknown -> true
    is IdeographicNodeDto.Glyph -> false
    is IdeographicNodeDto.Operator -> children.any { it.containsUnknownGlyph() }
}

class DecompositionParser {
    companion object {
        private const val UNKNOWN_GLYPH_CODEPOINT = 65311 // '？'
    }

    fun decompose(decomposition: String): IdeographicNodeDto {
        val codePoints = decomposition
            .codePoints().toArray()
        var index = 0

        fun parseNode(): IdeographicNodeDto {
            if (index >= codePoints.size) {
                throw DecompositionParseException(
                    "Unexpected end of string while parsing '$decomposition' at index $index"
                )
            }

            val cp = codePoints[index]
            val operator = IdeographicCharDto.fromCodepoint(cp)

            return when {
                operator != null -> {
                    index++
                    val children = List(operator.arity) { parseNode() }
                    IdeographicNodeDto.Operator(operator, children)
                }

                cp == UNKNOWN_GLYPH_CODEPOINT -> {
                    index++
                    IdeographicNodeDto.Unknown
                }

                else -> {
                    index++
                    IdeographicNodeDto.Glyph(cp)
                }
            }
        }

        val root = parseNode()
        if (index != codePoints.size) {
            throw DecompositionParseException(
                "Trailing characters after parsing '$decomposition': consumed $index of ${codePoints.size} codepoints"
            )
        }

        return root
    }
}
