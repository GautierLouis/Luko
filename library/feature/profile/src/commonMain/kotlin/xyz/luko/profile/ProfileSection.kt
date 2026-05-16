package xyz.luko.profile

import kotlinx.collections.immutable.ImmutableList
import xyz.luko.domain.repository.SettingTheme

internal enum class ProfileSection {
    Profile,
    Premium,
    Theme,
    Settings,
    Help,
}

internal sealed class SettingSection {
    data class Theme(
        val items: ImmutableList<SettingTheme>,
    ) : SettingSection()
}
