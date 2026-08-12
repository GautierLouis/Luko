package xyz.luko.fsrscore.model

enum class Grade(val value: Double, val severity: Int) {
    FORGOT(1.0, 0), HARD(2.0, 1), GOOD(3.0, 2), EASY(4.0, 3)
}
