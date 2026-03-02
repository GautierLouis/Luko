package com.louisgautier.designsystem.token.color

import androidx.compose.ui.graphics.Color

object ShadcnColors {

    object Light {
        val Background = Color(0xFFFFFFFF)            // oklch(1 0 0)
        val Foreground = Color(0xFF0A0A0A)            // oklch(0.145 0 0)

        val Card = Color(0xFFFFFFFF)                  // oklch(1 0 0)
        val CardForeground = Color(0xFF0A0A0A)        // oklch(0.145 0 0)

        val Popover = Color(0xFFFFFFFF)               // oklch(1 0 0)
        val PopoverForeground = Color(0xFF0A0A0A)     // oklch(0.145 0 0)

        val Primary = Color(0xFF171717)               // oklch(0.205 0 0)
        val PrimaryForeground = Color(0xFFFAFAFA)     // oklch(0.985 0 0)

        val Secondary = Color(0xFFF5F5F5)             // oklch(0.97 0 0)
        val SecondaryForeground = Color(0xFF171717)   // oklch(0.205 0 0)

        val Muted = Color(0xFFF5F5F5)                 // oklch(0.97 0 0)
        val MutedForeground = Color(0xFF737373)       // oklch(0.556 0 0)

        val Accent = Color(0xFFF5F5F5)                // oklch(0.97 0 0)
        val AccentForeground = Color(0xFF171717)      // oklch(0.205 0 0)

        val Destructive = Color(0xFFE7000B)          // oklch(0.577 0.245 27.325)

        val Border = Color(0xFFE5E5E5)                // oklch(0.922 0 0)
        val Input = Color(0xFFE5E5E5)                 // oklch(0.922 0 0)
        val Ring = Color(0xFFB5B5B5)                  // oklch(0.708 0 0)

        val Chart1 = Color(0xFF2D6BFF)                // oklch(0.646 0.222 41.116)
        val Chart2 = Color(0xFF00B38F)                // oklch(0.6 0.118 184.704)
        val Chart3 = Color(0xFF0066C6)                // oklch(0.398 0.07 227.392)
        val Chart4 = Color(0xFF67A400)                // oklch(0.828 0.189 84.429)
        val Chart5 = Color(0xFF5E8E00)                // oklch(0.769 0.188 70.08)

        val Sidebar = Color(0xFFF9F9F9)               // oklch(0.985 0 0)
        val SidebarForeground = Color(0xFF0A0A0A)     // oklch(0.145 0 0)
        val SidebarPrimary = Color(0xFF171717)        // oklch(0.205 0 0)
        val SidebarPrimaryForeground = Color(0xFFFAFAFA)// oklch(0.985 0 0)

        val SidebarAccent = Color(0xFFF5F5F5)         // oklch(0.97 0 0)
        val SidebarAccentForeground = Color(0xFF171717)// oklch(0.205 0 0)
        val SidebarBorder = Color(0xFFE5E5E5)         // oklch(0.922 0 0)
        val SidebarRing = Color(0xFFB5B5B5)           // oklch(0.708 0 0)
    }

    object Dark {
        val Background = Color(0xFF0A0A0A)            // oklch(0.145 0 0)
        val Foreground = Color(0xFFFAFAFA)            // oklch(0.985 0 0)

        val Card = Color(0xFF171717)                  // oklch(0.205 0 0)
        val CardForeground = Color(0xFFFAFAFA)        // oklch(0.985 0 0)

        val Popover = Color(0xFF262626)               // oklch(0.269 0 0)
        val PopoverForeground = Color(0xFFFAFAFA)     // oklch(0.985 0 0)

        val Primary =
            Color(0xFFEEEEEE)               // oklch(0.922 0 0)  (appears very light in dark scheme)
        val PrimaryForeground = Color(0xFF171717)     // oklch(0.205 0 0)

        val Secondary = Color(0xFF262626)             // oklch(0.269 0 0)
        val SecondaryForeground = Color(0xFFFAFAFA)   // oklch(0.985 0 0)

        val Muted = Color(0xFF262626)                 // oklch(0.269 0 0)
        val MutedForeground = Color(0xFFB5B5B5)       // oklch(0.708 0 0)

        val Accent = Color(0xFF404040)                // oklch(0.371 0 0)
        val AccentForeground = Color(0xFFFAFAFA)      // oklch(0.985 0 0)

        val Destructive = Color(0xFFFF1746)           // oklch(0.704 0.191 22.216)

        val Border = Color(0x1AFFFFFF)                // oklch(1 0 0 / 10%)
        val Input = Color(0x26FFFFFF)                 // oklch(1 0 0 / 15%)

        val Ring = Color(0xFF737373)                  // oklch(0.556 0 0)

        val Chart1 = Color(0xFF1447E6)                // oklch(0.488 0.243 264.376)
        val Chart2 = Color(0xFF00BC7D)                // oklch(0.696 0.17 162.48)
        val Chart3 = Color(0xFFFE9A00)                // oklch(0.769 0.188 70.08)
        val Chart4 = Color(0xFFAD46FF)                // oklch(0.627 0.265 303.9)
        val Chart5 = Color(0xFFFF2056)                // oklch(0.645 0.246 16.439)

        val Sidebar = Color(0xFF171717)               // oklch(0.205 0 0)
        val SidebarForeground = Color(0xFFFAFAFA)     // oklch(0.985 0 0)
        val SidebarPrimary = Color(0xFF1447E6)        // oklch(0.488 0.243 264.376)
        val SidebarPrimaryForeground = Color(0xFFFAFAFA)// oklch(0.985 0 0)

        val SidebarAccent = Color(0xFF262626)         // oklch(0.269 0 0)
        val SidebarAccentForeground = Color(0xFFFAFAFA)// oklch(0.985 0 0)
        val SidebarBorder = Color(0x1AFFFFFF)         // oklch(1 0 0 / 10%)
        val SidebarRing = Color(0xFF525252)           // oklch(0.439 0 0)
    }

    /**
     * Black / very-dark colors extracted from the theme (helpful for typographic scales / emphasis).
     * These are the theme variables that come out very-dark after OKLCH → sRGB conversion.
     */
    object BlackColors {
        val Foreground = Light.Foreground               // #0A0A0A
        val CardForeground = Light.CardForeground       // #0A0A0A
        val PopoverForeground = Light.PopoverForeground // #0A0A0A

        val PrimaryDark = Light.Primary                 // #171717
        val SecondaryForeground = Light.SecondaryForeground // #171717
        val AccentForeground = Light.AccentForeground   // #171717

        val SidebarForeground = Light.SidebarForeground // #0A0A0A
        val SidebarPrimary = Light.SidebarPrimary       // #171717
        val SidebarAccentForeground = Light.SidebarAccentForeground // #171717

        // dark-theme very-darks
        val DarkBackground = Dark.Background           // #0A0A0A
        val DarkCard = Dark.Card                       // #171717
        val DarkPopover = Dark.Popover                 // #262626
        val DarkSecondary = Dark.Secondary             // #262626
        val DarkMuted = Dark.Muted                     // #262626
        val DarkSidebarAccent = Dark.SidebarAccent     // #262626
    }
}
