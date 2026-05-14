package xyz.luko.server.domain.usecase.parser

sealed class IdeographicNode {
    data class Operator(val op: IdeographicChar, val children: List<IdeographicNode>) : IdeographicNode()
    data class Glyph(val code: Int) : IdeographicNode()
}