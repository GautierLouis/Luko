package com.louisgautier.profile

import com.louisgautier.domain.repository.SettingTheme
import kotlinx.collections.immutable.ImmutableList


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