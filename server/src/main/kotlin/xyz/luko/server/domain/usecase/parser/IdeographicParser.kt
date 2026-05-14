package xyz.luko.server.domain.usecase.parser

class IdeographicParser(input: String) {
    private val cps: IntArray = input.codePoints().toArray()
    private var i = 0

    private fun skipWhitespace() {
        while (i < cps.size && Character.isWhitespace(cps[i])) i++
    }

    private fun parseNode(): IdeographicNode {
        skipWhitespace()
        if (i >= cps.size) throw ParseException("Unexpected EOF at index $i")
        val cp = cps[i]
        val ide = IdeographicChar.fromCodepoint(cp)
        if (ide != null) {
            i++
            val children = mutableListOf<IdeographicNode>()
            repeat(ide.arity) {
                skipWhitespace()
                if (i >= cps.size) throw ParseException("Operator ${ide.symbol} expects ${ide.arity} children but found EOF")
                children.add(parseNode())
            }
            return IdeographicNode.Operator(ide, children)
        } else {
            // glyph: return single codepoint as Glyph (handles '？' and CJK chars)
//            val ch = String(Character.toChars(cp)).first()
            i++
            return IdeographicNode.Glyph(cp)
        }
    }

    fun parse(): IdeographicNode {
        i = 0
        skipWhitespace()
        val node = parseNode()
        skipWhitespace()
        if (i < cps.size) throw ParseException("Trailing text after parse at index $i (remaining: ${remaining()})")
        return node
    }

    private fun remaining(): String {
        if (i >= cps.size) return ""
        val sb = StringBuilder()
        for (j in i until cps.size) sb.appendCodePoint(cps[j])
        return sb.toString()
    }

    class ParseException(message: String) : RuntimeException(message)
}