package xyz.luko.domain.model

data class EndOfSessionStats(
    val lastSession: Session,
    val overhaulAccuracy: Float,
    val oldStreak: Int,
    val newStreak: Int
)
