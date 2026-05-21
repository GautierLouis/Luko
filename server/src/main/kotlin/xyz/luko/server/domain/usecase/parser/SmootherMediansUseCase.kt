package xyz.luko.server.domain.usecase.parser

import xyz.luko.apicontracts.dto.StrokeDto


class SmootherMediansUseCase {
    fun smoothen(medians: List<List<List<Float>>>): List<StrokeDto> {
        return medians.map { s ->
            val normalized = s.map { p -> (p[0] * 1000f / 1024f) to (p[1] * 1000f / 1024f) }
            CatmullRomToBezier.toPoints(RamerDouglasPeucker.simplify(normalized))
        }
    }
}
