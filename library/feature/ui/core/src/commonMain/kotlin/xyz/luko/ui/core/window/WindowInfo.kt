package xyz.luko.ui.core.window

enum class WindowInfo {
    COMPACT,   // phone portrait
    MEDIUM,    // phone landscape, small tablet portrait
    EXPANDED;   // tablet landscape, desktop

    fun isCompact() = this == COMPACT
    fun isMedium() = this == MEDIUM
    fun isExpanded() = this == EXPANDED
}
