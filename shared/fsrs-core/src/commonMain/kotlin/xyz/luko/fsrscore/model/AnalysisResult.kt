package xyz.luko.fsrscore.model


data class AnalysisResult(
    val strokeComparison: StrokeComparisonResult,
    val grade: Grade,
    val fsrsResult: FsrsResult,
)
