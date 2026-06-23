package xyz.luko.ui.core.window

enum class WindowInfo {
    WIDTH_COMPACT, HEIGHT_COMPACT,  // phone portrait, phone landscape
    MEDIUM,    // small tablet portrait
    EXPANDED;   // tablet landscape, desktop

    fun isCompact() = this == WIDTH_COMPACT
    fun isHeightCompact() = this == HEIGHT_COMPACT
    fun isMedium() = this == MEDIUM
    fun isExpanded() = this == EXPANDED
}
