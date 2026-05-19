package xyz.luko.server.domain.usecase.parser

class SmotherMediansUseCase {
    fun smoothen(medians: List<List<List<Float>>>): List<String> {
        return medians.map { s ->
            val normalized = s.map { p -> (p[0] * 1000f / 1024f) to (p[1] * 1000f / 1024f) }
            CatmullRomToBezier.toPath(RamerDouglasPeucker.simplify(normalized))
        }
    }
}
