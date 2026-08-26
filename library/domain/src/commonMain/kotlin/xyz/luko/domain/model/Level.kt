package xyz.luko.domain.model

/**
 * Level of proficiency of a character.
 */
enum class Level(val rank: Int) {
    NEVER_PRACTICED(0),
    BEGINNER(1),
    LEARNER(2),
    PRACTITIONER(3),
    SKILLED(4),
    PROFICIENT(5),
    MASTERED(6);

    companion object {
        fun fromRank(rank: Int): Level =
            Level.entries.firstOrNull { it.rank == rank } ?: NEVER_PRACTICED
    }
}
