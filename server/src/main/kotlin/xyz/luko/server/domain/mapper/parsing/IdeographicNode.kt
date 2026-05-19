package xyz.luko.server.domain.mapper.parsing

sealed class IdeographicNode {
    data class Operator(val op: IdeographicChar, val children: List<IdeographicNode>) : IdeographicNode()
    data class Glyph(val code: Int) : IdeographicNode()
}
