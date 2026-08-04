package xyz.luko.server.router.route

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.v1.core.JoinType
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.select
import xyz.luko.apicontracts.dto.AppHeader.APP_PLATFORM
import xyz.luko.apicontracts.dto.AuthRegistrationDto
import xyz.luko.apicontracts.dto.FcmUpdateDto
import xyz.luko.apicontracts.routing.Destination
import xyz.luko.server.data.database.table.CharacterComplexityTable
import xyz.luko.server.data.database.table.CharacterFsrsStateTable
import xyz.luko.server.data.database.table.UserTable
import xyz.luko.server.domain.repo.UserRepository
import xyz.luko.server.error.ErrorCode.NO_RESULT
import xyz.luko.server.error.NotResultException
import xyz.luko.server.plugin.BEARER
import xyz.luko.server.router.RouteController
import xyz.luko.server.router.respondOk
import xyz.luko.fsrscore.AnalyseResult
import xyz.luko.fsrscore.AttemptSignal
import xyz.luko.fsrscore.FsrsState
import xyz.luko.fsrscore.ReviewAttemptRequest
import kotlin.time.Clock
import kotlin.time.Instant

class AuthedRouteController(
    private val userRepository: UserRepository,
    private val analyseResult: AnalyseResult
) : RouteController {
    override fun Route.register() {
        authenticate(BEARER) {
            post<Destination.RegisterAnonymously> {
                val principal = call.principal<UserIdPrincipal>()!!
                val body = call.receive<AuthRegistrationDto>()
                val platform = call.request.headers[APP_PLATFORM]!!

                userRepository.registerAnonymously(
                    uid = principal.name,
                    platform = platform,
                    body = body
                )

                call.respond(HttpStatusCode.OK)
            }

            post<Destination.UpdateFcm> {
                val principal = call.principal<UserIdPrincipal>()!!
                val body = call.receive<FcmUpdateDto>()

                userRepository.updateFcm(principal.name, body)

                call.respond(HttpStatusCode.NoContent)
            }

            get<Destination.Me> {
                val principal = call.principal<UserIdPrincipal>()!!
                val user = userRepository.getUser(principal.name)
                    ?: throw NotResultException(NO_RESULT, "No user found")
                call.respondOk(user)
            }

            post<Destination.Me.ReviewSession> {
                val principal = call.principal<UserIdPrincipal>()!!
                val user = userRepository.getUser(principal.name)
                    ?: throw NotResultException(NO_RESULT, "No user found")
                val body = call.receive<ReviewAttemptRequest>()

                body.responses.forEach { response ->

                    val complexity = CharacterComplexityTable
                        .select(CharacterComplexityTable.complexityFactor)
                        .where { CharacterComplexityTable.code eq response.characterCode }
                        .limit(1)
                        .take(1)
                        .map { it[CharacterComplexityTable.complexityFactor] }
                        .first()

                    val fsrs = CharacterFsrsStateTable
                        .join(
                            otherTable = UserTable,
                            joinType = JoinType.INNER,
                            onColumn = UserTable.id,
                            otherColumn = CharacterFsrsStateTable.userId
                        )
                        .select(
                            UserTable.id,
                            CharacterFsrsStateTable.stability,
                            CharacterFsrsStateTable.difficulty,
                            CharacterFsrsStateTable.lastReviewedAt,
                            CharacterFsrsStateTable.nextReviewDueAt
                        )
                        .where {
                            (UserTable.firebaseUid eq user.id) and
                                (CharacterFsrsStateTable.characterCode eq response.characterCode)
                        }
                        .limit(1)
                        .take(1)
                        .map {
                            Triple(
                                it[UserTable.id],
                                FsrsState(
                                    difficulty = it[CharacterFsrsStateTable.difficulty],
                                    stability = it[CharacterFsrsStateTable.stability]
                                ),
                                it[CharacterFsrsStateTable.lastReviewedAt]
                            )
                        }
                        .firstOrNull()


                    val signal = AttemptSignal(
                        characterCode = response.characterCode,
                        strokes = response.strokes,
                        referenceStrokes = response.strokes,
                        recognitionResult = response.recognitionResult,
                        resetCount = response.resetCount,
                        durationMs = response.durationMs,
                        complexityFactor = complexity,
                        practiceMode = response.practiceMode,
                        fsrsState = fsrs?.second,
                        elapsedDays = fsrs?.third?.let { lastReviewMilli ->
                            val lastReview = Instant.fromEpochMilliseconds(lastReviewMilli).toLocalDateTime(TimeZone.UTC)
                            val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)
                            lastReview.date.daysUntil(now.date).toDouble()
                        }
                    )
                    analyseResult.analyse(signal)
                }
            }
        }
    }
}
