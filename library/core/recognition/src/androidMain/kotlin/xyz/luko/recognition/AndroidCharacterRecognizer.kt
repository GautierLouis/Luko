package xyz.luko.recognition

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognition
import com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognitionModel
import com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognitionModelIdentifier
import com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognizerOptions
import com.google.mlkit.vision.digitalink.recognition.Ink
import kotlinx.coroutines.tasks.await

internal class AndroidCharacterRecognizer : CharacterRecognizer {

    private val modelIdentifier = DigitalInkRecognitionModelIdentifier.fromLanguageTag(
        DigitalInkRecognitionModelIdentifier.ZH_HANI.languageTag
    ) ?: error("Unsupported language tag")
    private val model = DigitalInkRecognitionModel.builder(modelIdentifier).build()
    private val modelManager = RemoteModelManager.getInstance()

    private val recognizer by lazy {
        DigitalInkRecognition.getClient(DigitalInkRecognizerOptions.builder(model).build())
    }

    override suspend fun ensureReady(): Result<Unit> = runCatching {
        val isDownloaded = modelManager.isModelDownloaded(model).await()
        if (!isDownloaded) {
            modelManager.download(model, DownloadConditions.Builder().build()).await()
        }
    }

    override suspend fun recognize(strokes: List<RecognizableStroke>): Result<RecognitionResult> =
        runCatching {
            val inkBuilder = Ink.builder()

            strokes.forEach { stroke ->
                val strokeBuilder = Ink.Stroke.builder()
                stroke.points.forEach { p ->
                    strokeBuilder.addPoint(Ink.Point.create(p.x, p.y, p.timestampMs))
                }
                inkBuilder.addStroke(strokeBuilder.build())
            }

            val result = recognizer.recognize(inkBuilder.build()).await()

            RecognitionResult(candidates = result.candidates.mapNotNull { it.text })
        }
}
