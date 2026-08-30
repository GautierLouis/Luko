package xyz.luko.server.domain.repo

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import xyz.luko.apicontracts.dto.DictionaryDto
import xyz.luko.apicontracts.dto.ResponseSessionDto
import xyz.luko.server.data.database.dao.DictionaryDao
import xyz.luko.server.data.database.dao.SeedDao
import xyz.luko.server.data.database.table.DictionaryTable
import xyz.luko.server.domain.mapper.ResultRowMapping.toDictionary
import xyz.luko.server.domain.mapper.ResultRowMapping.toSeedDto

/**
 * Builds a review session's character list from a persisted seed.
 *
 * A session is filled in two tiers, in priority order:
 *
 * 1. **Due or new characters** — anything already due for review
 *    ([CharacterFsrsStateTable.nextReviewDueAt] in the past) or never
 *    reviewed before (`nextReviewDueAt` is `null`). Selected via
 *    [DictionaryDao.getDueOrNewCharacters], randomized using the
 *    session's [seed] so the same seed always reproduces the same
 *    character set and order (used by session replay).
 *
 * 2. **Review-ahead (upcoming) fill** — if tier 1 doesn't fill the
 *    session's target size, the shortfall is topped up with characters
 *    whose next review isn't due yet, ordered by soonest
 *    `nextReviewDueAt` first. This trades a small, well-understood
 *    FSRS cost (reviewing slightly early) for consistently sized
 *    sessions, rather than returning a short session whenever a
 *    user's queue is temporarily thin.
 *
 * If the user's entire deck (across the requested [SeedRow.levels])
 * has fewer characters than [SeedRow.limit] even after both tiers,
 * the returned list is shorter than requested — this is expected
 * for small or newly-started decks and is not treated as an error.
 *
 * @param id the user resolving this session.
 * @param seed identifies a previously persisted [SeedRow] (its
 *   levels, limit, and random seed value). Reused as-is for session
 *   replay; freshly generated and persisted by the caller for a new
 *   session — see [DefaultSessionRepository.createNewSession] and
 *   [DefaultSessionRepository.replaySession].
 * @return the session's characters (up to [SeedRow.limit]) alongside
 *   the [seed] used to build it.
 * @throws IllegalArgumentException if no [SeedRow] exists for [seed].
 */
internal class BuildSessionUseCase(
    private val dictionaryDao: DictionaryDao,
    private val seedDao: SeedDao,
) {
    suspend fun execute(id: EntityID<Int>, seed: Long): ResponseSessionDto<DictionaryDto> {

        val seedRow = seedDao.getSeed(seed)
            ?.toSeedDto()
            ?: throw IllegalArgumentException("seed not found")

        val levels = seedRow.levels.split(",").map { it.toInt() }

        val dueAndNew = dictionaryDao.getDueOrNewCharacters(id, levels, seedRow.limit, seed)
        val remaining = seedRow.limit - dueAndNew.size

        val result = if (remaining > 0) {
            val excludedCodes = dueAndNew.map { it[DictionaryTable.code] }
            val reviewAhead =
                dictionaryDao.getUpcomingCharacters(id, levels, remaining, excludedCodes)
            dueAndNew + reviewAhead
        } else {
            dueAndNew
        }

        return ResponseSessionDto(seed, result.map { it.toDictionary() })
    }
}
