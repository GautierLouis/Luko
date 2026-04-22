package com.louisgautier.server.domain.repo

import com.louisgautier.apicontracts.dto.DecompositionDto
import com.louisgautier.server.domain.usecase.parser.IdeographicNode
import com.louisgautier.server.domain.usecase.parser.IdeographicParser

class CompositionUseCase {
    fun decompose(original: String): List<DecompositionDto> {
        return try {
            val parser = IdeographicParser(original)
            val tree = parser.parse()
            decompositionOrderedJsonFromIds(tree)
        } catch (_: Throwable) {
            emptyList()
        }
    }

    private fun decompositionOrderedJsonFromIds(
        node: IdeographicNode,
    ): List<DecompositionDto> {
        val decompositionMap = linkedMapOf<Int, MutableList<Int>>()

        fun walk(n: IdeographicNode) {
            when (n) {
                is IdeographicNode.Glyph -> { /* nothing to emit at glyph nodes */
                }

                is IdeographicNode.Operator -> {
                    // For children, if child is Glyph -> child char, if Operator -> operator symbol
                    val childrenStrings = n.children.map { child ->
                        when (child) {
                            is IdeographicNode.Glyph -> child.code
                            is IdeographicNode.Operator -> child.op.symbol
                        }
                    }

                    val list = decompositionMap.getOrPut(n.op.symbol) { mutableListOf() }
                    list.addAll(childrenStrings)

                    // then recurse into children so later operators are processed in preorder
                    n.children.forEach { walk(it) }
                }
            }
        }

        walk(node)

        return decompositionMap.map { DecompositionDto(it.key, it.value) }
    }
}