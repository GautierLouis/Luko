package xyz.luko.server.domain.usecase

import xyz.luko.apicontracts.dto.StrokeDto


class StrokeRenderingUseCase {
    fun execute(medians: List<List<List<Float>>>): List<StrokeDto> {
        return medians.map { s ->
            val normalized = s.map { p -> (p[0] * 1000f / 1024f) to (p[1] * 1000f / 1024f) }
            BezierCurveConverter.execute(PointReducer.execute(normalized))
        }
    }
}
