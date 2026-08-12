package xyz.luko.fsrscore.model


data class AnalysisResult(
    val accuracy: StrokeComparisonResult,
    val grade: Grade,
    val fsrsResult: FsrsResult,
)
