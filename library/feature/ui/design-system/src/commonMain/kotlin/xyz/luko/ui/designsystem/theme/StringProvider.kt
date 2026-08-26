package xyz.luko.ui.designsystem.theme

import androidx.compose.runtime.ProvidableCompositionLocal
import xyz.luko.ui.designsystem.token.string.StringsLocale

/**
 * Marker interface for a feature's localized string bundle.
 *
 * Each feature owns exactly one [AppString] implementation (e.g. `OnboardingStrings`,
 * `LearningStrings`) containing only the strings that feature needs. This is what lets
 * the monolithic `Strings` god object be split apart: `:feature:ui` depends on this
 * interface, never on a concrete feature's string bundle, so features stay free to
 * evolve their own strings without touching `:feature:ui` or any other feature.
 *
 * Implementations should be `@Immutable` data classes and are expected to be
 * `internal` to their owning feature module — only the associated [FeatureStringBinding]
 * needs to be exposed publicly.
 */
interface AppString

/**
 * Resolves a locale to a concrete, fully-populated [AppString] bundle.
 *
 * This is the seam between "which language is active" (decided centrally by
 * `AppTheme`/`:feature:ui`) and "what the strings actually say" (decided by the
 * owning feature). `:feature:ui` calls [get] without knowing or caring what `T` is —
 * it just needs *a* value to provide into composition for the given locale.
 *
 * One [StringProvider] exists per feature string bundle, typically implemented
 * inline as part of that feature's [FeatureStringBinding].
 */
interface StringProvider<T : AppString> {
    fun get(locale: StringsLocale): T
}

/**
 * A feature's complete contribution to the app-wide string system: the
 * [CompositionLocal] its strings are read from, paired with the [StringProvider]
 * that resolves those strings for a given locale.
 *
 * `:app` collects one [FeatureStringBinding] per feature into a `List<FeatureStringBinding<*>>`
 * and hands it to `AppTheme`. `AppTheme` provides every binding's [local] into composition
 * without ever referencing the concrete `T` — it only sees `AppString`. Each feature then reads its
 * own [local] internally (typically via a small `Theme.xStrings` accessor) and gets
 * back its own type, with no cast required.
 *
 * This is the whole point of the split: `:feature:ui` aggregates and provides strings
 * for every feature without ever depending on a single feature module, and each
 * feature gets fully-typed access to only the strings it owns.
 */
interface FeatureStringBinding<T : AppString> {
    val local: ProvidableCompositionLocal<T>
    val provider: StringProvider<T>
}
