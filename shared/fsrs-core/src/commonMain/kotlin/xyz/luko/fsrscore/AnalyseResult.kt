package xyz.luko.fsrscore

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.koin.dsl.module
import xyz.luko.apicontracts.dto.StrokeDto


@Serializable
enum class RecognitionResult { FAILURE, SUCCESS, PARTIAL }

@Serializable
data class ReviewAttemptRequest(
    val doneAt: LocalDateTime,
    val responses: List<ReviewResponseRequest>
)

@Serializable
data class ReviewResponseRequest(
    val characterCode: Int,
    val strokes: List<StrokeDto>,
    val recognitionResult: RecognitionResult,
    val resetCount: Int,
    val durationMs: Long,
    val practiceMode: PracticeMode,
)

@Serializable
data class AttemptSignal(
    val characterCode: Int,
    val strokes: List<StrokeDto>,
    val referenceStrokes: List<StrokeDto>,
    val recognitionResult: RecognitionResult,
    val resetCount: Int,
    val durationMs: Long,
    val complexityFactor: Double,
    val practiceMode: PracticeMode,
    val fsrsState: FsrsState? = null,
    val elapsedDays: Double? = 0.0,
)

@Serializable
enum class PracticeMode { EASY, MEDIUM, HARD }

@Serializable
data class FsrsState(val difficulty: Double, val stability: Double)


val fsrsModule = module {
    factory { ComputeNextFsrsStateUseCase() }
    factory { AccuracyUseCase() }
    factory { GradeUseCase(get()) }
    factory { AnalyseResult(get(), get(), get()) }
    factory { CharacterDurationUseCase() }
}

class AnalyseResult(
    private val fsrsStateUseCase: ComputeNextFsrsStateUseCase,
    private val accuracyUseCase: AccuracyUseCase,
    private val gradeUseCase: GradeUseCase,
) {

    fun analyse(
        signal: AttemptSignal
    ) {
        //run stroke comparison
        val accuracyResult = accuracyUseCase.calculate(
            reference = signal.referenceStrokes
                .map { s -> s.points.map { Offset(it.x, it.y) } },
            userStroke = signal.strokes
                .map { s -> s.points.map { Offset(it.x, it.y) } },
        )

        //compute Gradle
        val grade = gradeUseCase.deriveGrade(
            signals = signal,
            stroke = accuracyResult
        )

        //then run fsrs
        fsrsStateUseCase.invoke(
            current = signal.fsrsState,
            grade = grade,
            elapsedDays = signal.elapsedDays ?: 0.0,
        )


        //then insert user progress

        //then return result
    }
}
