package xyz.luko.home.strings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import xyz.luko.ui.designsystem.theme.AppString
import xyz.luko.ui.designsystem.theme.FeatureStringBinding
import xyz.luko.ui.designsystem.theme.StringProvider
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.designsystem.token.string.StringsLocale

@Immutable
data class LearningStrings(
    val cardDictionary: String,
    val continueSessionQsCount: (Int) -> String,
    val character: String,
    val ongoingSync: String,
    val failedSync: String,
    val retrySync: String,
) : AppString

val learningStringProvider: FeatureStringBinding<*> = LearningFeatureStringBindingImpl

internal object LearningFeatureStringBindingImpl : FeatureStringBinding<LearningStrings> {
    override val local
        get() = LocalLearningString
    override val provider = object : StringProvider<LearningStrings> {
        override fun get(locale: StringsLocale) = when (locale) {
            StringsLocale.EN -> getEN()
            StringsLocale.FR -> getFR()
        }
    }
}

private fun getEN() = LearningStrings(
    cardDictionary = "Character Dictionary",
    continueSessionQsCount = { "$it QS" },
    character = "Character",
    ongoingSync = "Downloading pre-requisite...",
    failedSync = "Failed to download pre-requisite",
    retrySync = "Retry",
)

private fun getFR() = LearningStrings(
    cardDictionary = "Dictionnaire des caractères",
    continueSessionQsCount = { "$it QS" },
    character = "Caractère",
    ongoingSync = "Téléchargement des pré-requis...",
    failedSync = "Échec du téléchargement des pré-requis",
    retrySync = "Réessayer",
)

private val LocalLearningString = staticCompositionLocalOf { getEN() }

internal val Theme.learningStrings: LearningStrings
    @Composable
    get() = LocalLearningString.current

