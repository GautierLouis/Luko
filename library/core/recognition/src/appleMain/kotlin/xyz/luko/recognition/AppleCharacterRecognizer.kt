package xyz.luko.recognition


import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreGraphics.CGContextAddLineToPoint
import platform.CoreGraphics.CGContextBeginPath
import platform.CoreGraphics.CGContextFillRect
import platform.CoreGraphics.CGContextMoveToPoint
import platform.CoreGraphics.CGContextSetFillColorWithColor
import platform.CoreGraphics.CGContextSetLineCap
import platform.CoreGraphics.CGContextSetLineJoin
import platform.CoreGraphics.CGContextSetLineWidth
import platform.CoreGraphics.CGContextSetStrokeColorWithColor
import platform.CoreGraphics.CGContextStrokePath
import platform.CoreGraphics.CGLineCap
import platform.CoreGraphics.CGLineJoin
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.UIKit.UIColor
import platform.UIKit.UIGraphicsBeginImageContextWithOptions
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIGraphicsGetCurrentContext
import platform.UIKit.UIGraphicsGetImageFromCurrentImageContext
import platform.UIKit.UIImage
import platform.Vision.VNImageRequestHandler
import platform.Vision.VNRecognizeTextRequest
import platform.Vision.VNRecognizedText
import platform.Vision.VNRecognizedTextObservation
import platform.Vision.VNRequestTextRecognitionLevelAccurate
import kotlin.coroutines.resume

@OptIn(ExperimentalForeignApi::class)
class AppleCharacterRecognizer : CharacterRecognizer {

    override suspend fun ensureReady(): Result<Unit> =
        Result.success(Unit) // no model download needed

    override suspend fun recognize(strokes: List<RecognizableStroke>): Result<RecognitionResult> =
        runCatching {
            val cgImage = rasterize(strokes)?.CGImage ?: error("Failed to rasterize strokes")

            suspendCancellableCoroutine { cont ->
                val request = VNRecognizeTextRequest { request, error ->
                    if (error != null) {
                        cont.resume(RecognitionResult(emptyList()))
                        return@VNRecognizeTextRequest
                    }
                    val observations = request?.results as? List<VNRecognizedTextObservation>
                    val candidates = observations
                        ?.firstOrNull()
                        ?.topCandidates(6u)
                        ?.filterIsInstance<VNRecognizedText>()
                        ?.map { it.string }
                        .orEmpty()

                    cont.resume(
                        RecognitionResult(candidates)
                    )
                }
                request.recognitionLevel = VNRequestTextRecognitionLevelAccurate
                request.recognitionLanguages = listOf("zh-Hans")
                request.usesLanguageCorrection = false

                val handler =
                    VNImageRequestHandler(cGImage = cgImage, options = emptyMap<Any?, Any>())
                handler.performRequests(listOf(request), null)
            }
        }

    private fun rasterize(strokes: List<RecognizableStroke>): UIImage? {
        UIGraphicsBeginImageContextWithOptions(CGSizeMake(1024.0, 1024.0), false, 1.0)
        val context = UIGraphicsGetCurrentContext() ?: run {
            UIGraphicsEndImageContext(); return null
        }

        CGContextSetFillColorWithColor(context, UIColor.whiteColor.CGColor)
        CGContextFillRect(context, CGRectMake(0.0, 0.0, 1024.0, 1024.0))

        CGContextSetStrokeColorWithColor(context, UIColor.blackColor.CGColor)
        CGContextSetLineWidth(context, 8.0)
        CGContextSetLineCap(context, CGLineCap.kCGLineCapRound)
        CGContextSetLineJoin(context, CGLineJoin.kCGLineJoinRound)

        strokes.forEach { stroke ->
            if (stroke.points.isEmpty()) return@forEach
            CGContextBeginPath(context)
            val first = stroke.points.first()
            CGContextMoveToPoint(context, first.x.toDouble(), first.y.toDouble())
            stroke.points.drop(1).forEach { p ->
                CGContextAddLineToPoint(context, p.x.toDouble(), p.y.toDouble())
            }
            CGContextStrokePath(context)
        }

        val image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return image
    }
}
