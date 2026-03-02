package com.louisgautier.designsystem.ai

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


val Green600 = Color(0xFF16A34A)        // text-green-600
val Green50 = Color(0xFFF0FDF4)         // bg-green-50
val Green500 = Color(0xFF22C55E)        // border-green-500
val Green100 = Color(0xFFDCFCE7)        // bg-green-100
val Green700 = Color(0xFF15803D)        // text-green-700

val Amber600 = Color(0xFFD97706)        // text-amber-600
val Amber50 = Color(0xFFFFFBEB)         // bg-amber-50
val Amber500 = Color(0xFFF59E0B)        // border-amber-500
val Amber100 = Color(0xFFFEF3C7)        // bg-amber-100
val Amber700 = Color(0xFFB45309)        // text-amber-700

val Orange600 = Color(0xFFEA580C)       // text-orange-600
val Orange50 = Color(0xFFFFF7ED)        // bg-orange-50
val Orange500 = Color(0xFFF97316)       // border-orange-500
val Orange100 = Color(0xFFFFEDD5)       // bg-orange-100
val Orange700 = Color(0xFFC2410C)       // text-orange-700

val Purple600 = Color(0xFF9333EA)       // text-purple-600
val Purple50 = Color(0xFFFAF5FF)        // bg-purple-50
val Purple500 = Color(0xFFA855F7)       // border-purple-500
val Purple100 = Color(0xFFF3E8FF)       // bg-purple-100
val Purple700 = Color(0xFF7E22CE)       // text-purple-700

val Teal600 = Color(0xFF0D9488)         // text-teal-600
val Teal50 = Color(0xFFF0FDFA)          // bg-teal-50
val Teal100 = Color(0xFFCCFBF1)
val Teal700 = Color(0xFF0F766E)
val Teal500 = Color(0xFF14B8A6)         // border-teal-500
val Teal300 = Color(0xFF5EEAD4)         // border-teal-300 (canvas)

val Red600 = Color(0xFFDC2626)          // text-red-600
val Red50 = Color(0xFFFEF2F2)           // bg-red-50
val Red500 = Color(0xFFEF4444)          // border-red-500
val Red100 = Color(0xFFFEE2E2)          // bg-red-100
val Red700 = Color(0xFFB91C1C)          // text-red-700

val Yellow600 = Color(0xFFCA8A04)       // text-yellow-600
val Yellow50 = Color(0xFFFEFCE8)        // bg-yellow-50
val Yellow500 = Color(0xFFEAB308)       // border-yellow-500
val Yellow100 = Color(0xFFFEF9C3)       // bg-yellow-100
val Yellow700 = Color(0xFFA16207)       // text-yellow-700

//val Teal50 = Color(0xFFF0FDFA)          // gradient start
val Emerald50 = Color(0xFFECFDF5)       // gradient end

//val Teal500 = Color(0xFF14B8A6)         // button gradient start
val Emerald600 = Color(0xFF059669)      // button gradient end

//val Teal600 = Color(0xFF0D9488)         // button hover start
val Emerald700 = Color(0xFF047857)      // button hover end

val Gray900 = Color(0xFF111827)         // text-gray-900
val Gray700 = Color(0xFF374151)         // text-gray-700
val Gray600 = Color(0xFF4B5563)         // text-gray-600
val Gray500 = Color(0xFF6B7280)         // text-gray-500
val Gray400 = Color(0xFF9CA3AF)         // text-gray-400
val Gray200 = Color(0xFFE5E7EB)         // border-gray-200
val Gray100 = Color(0xFFF3F4F6)         // bg-gray-100
val Gray50 = Color(0xFFF9FAFB)          // bg-gray-50
val White = Color(0xFFFFFFFF)           // bg-white

val DarkBackground = Color(0xFF0F172A)        // slate-900 - main background
val DarkSurface = Color(0xFF1E293B)           // slate-800 - cards, surfaces
val DarkSurfaceVariant = Color(0xFF334155)    // slate-700 - elevated surfaces

val DarkTextPrimary = Color(0xFFF1F5F9)       // slate-100 - primary text
val DarkTextSecondary = Color(0xFFCBD5E1)     // slate-300 - secondary text
val DarkTextTertiary = Color(0xFF94A3B8)      // slate-400 - tertiary text
val DarkTextDisabled = Color(0xFF64748B)      // slate-500 - disabled text

val DarkGreen400 = Color(0xFF4ADE80)          // text-green-400 - brighter for dark bg
val DarkGreen950 = Color(0xFF052E16)          // bg-green-950 - dark background
val DarkGreen500 = Color(0xFF22C55E)          // border-green-500
val DarkGreen900 = Color(0xFF14532D)          // bg-green-900 - badge background
val DarkGreen300 = Color(0xFF86EFAC)          // text-green-300 - badge text

val DarkAmber400 = Color(0xFFFBBF24)          // text-amber-400
val DarkAmber950 = Color(0xFF451A03)          // bg-amber-950
val DarkAmber500 = Color(0xFFF59E0B)          // border-amber-500
val DarkAmber900 = Color(0xFF78350F)          // bg-amber-900
val DarkAmber300 = Color(0xFFFCD34D)          // text-amber-300

val DarkOrange400 = Color(0xFFFB923C)         // text-orange-400
val DarkOrange950 = Color(0xFF431407)         // bg-orange-950
val DarkOrange500 = Color(0xFFF97316)         // border-orange-500
val DarkOrange900 = Color(0xFF7C2D12)         // bg-orange-900
val DarkOrange300 = Color(0xFFFDBA74)         // text-orange-300

val DarkPurple400 = Color(0xFFC084FC)         // text-purple-400
val DarkPurple950 = Color(0xFF3B0764)         // bg-purple-950
val DarkPurple500 = Color(0xFFA855F7)         // border-purple-500
val DarkPurple900 = Color(0xFF581C87)         // bg-purple-900
val DarkPurple300 = Color(0xFFD8B4FE)         // text-purple-300

val DarkTeal400 = Color(0xFF2DD4BF)           // text-teal-400
val DarkTeal950 = Color(0xFF042F2E)           // bg-teal-950
val DarkTeal500 = Color(0xFF14B8A6)           // border-teal-500
val DarkTeal900 = Color(0xFF134E4A)           // bg-teal-900
val DarkTeal300 = Color(0xFF5EEAD4)           // canvas border

val DarkRed400 = Color(0xFFF87171)            // text-red-400
val DarkRed950 = Color(0xFF450A0A)            // bg-red-950
val DarkRed500 = Color(0xFFEF4444)            // border-red-500
val DarkRed900 = Color(0xFF7F1D1D)            // bg-red-900
val DarkRed300 = Color(0xFFFCA5A5)            // text-red-300

val DarkYellow400 = Color(0xFFFACC15)         // text-yellow-400
val DarkYellow950 = Color(0xFF422006)         // bg-yellow-950
val DarkYellow500 = Color(0xFFEAB308)         // border-yellow-500
val DarkYellow900 = Color(0xFF713F12)         // bg-yellow-900
val DarkYellow300 = Color(0xFFFDE047)         // text-yellow-300

//val DarkTeal950 = Color(0xFF042F2E)           // gradient start (very dark teal)
val DarkEmerald950 = Color(0xFF022C22)        // gradient end (very dark emerald)

//val DarkTeal500 = Color(0xFF14B8A6)           // button gradient start (same)
val DarkEmerald600 = Color(0xFF059669)        // button gradient end (same)
//val DarkTeal400 = Color(0xFF2DD4BF)           // selected/active state

val DarkBorder = Color(0xFF334155)            // slate-700 - default borders
val DarkBorderLight = Color(0xFF475569)       // slate-600 - lighter borders
val DarkDivider = Color(0xFF1E293B)           // slate-800 - dividers

val darkBackgroundGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF042F2E), // dark-teal-950
        Color(0xFF022C22)  // dark-emerald-950
    )
)
val darkButtonGradient = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF14B8A6), // teal-500
        Color(0xFF059669)  // emerald-600
    )
)

val darkCardGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF1E293B), // slate-800
        Color(0xFF042F2E)  // dark-teal-950
    ),
    start = Offset(0f, 0f),
    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
)

val darkCanvasGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF334155), // slate-700
        Color(0xFF1E293B)  // slate-800
    )
)

data class NewAppColors(
    // Backgrounds
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,

    // Text
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,

    // Character Groups
    val commonText: Color,
    val commonBg: Color,
    val commonBorder: Color,

    val frequentText: Color,
    val frequentBg: Color,
    val frequentBorder: Color,

    val standardText: Color,
    val standardBg: Color,
    val standardBorder: Color,

    val extendedText: Color,
    val extendedBg: Color,
    val extendedBorder: Color,

    val customText: Color,
    val customBg: Color,
    val customBorder: Color,

    // Borders
    val border: Color,
    val borderLight: Color,
)

val LightColors = NewAppColors(
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFF9FAFB),
    textPrimary = Color(0xFF111827),
    textSecondary = Color(0xFF4B5563),
    textTertiary = Color(0xFF6B7280),
    commonText = Color(0xFF16A34A),
    commonBg = Color(0xFFF0FDF4),
    commonBorder = Color(0xFF22C55E),
    frequentText = Color(0xFFD97706),
    frequentBg = Color(0xFFFFFBEB),
    frequentBorder = Color(0xFFF59E0B),
    standardText = Color(0xFFEA580C),
    standardBg = Color(0xFFFFF7ED),
    standardBorder = Color(0xFFF97316),
    extendedText = Color(0xFF9333EA),
    extendedBg = Color(0xFFFAF5FF),
    extendedBorder = Color(0xFFA855F7),
    customText = Color(0xFF0D9488),
    customBg = Color(0xFFF0FDFA),
    customBorder = Color(0xFF14B8A6),
    border = Color(0xFFE5E7EB),
    borderLight = Color(0xFFF3F4F6),
)

val DarkColors = NewAppColors(
    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B),
    surfaceVariant = Color(0xFF334155),
    textPrimary = Color(0xFFF1F5F9),
    textSecondary = Color(0xFFCBD5E1),
    textTertiary = Color(0xFF94A3B8),
    commonText = Color(0xFF4ADE80),
    commonBg = Color(0xFF052E16),
    commonBorder = Color(0xFF22C55E),
    frequentText = Color(0xFFFBBF24),
    frequentBg = Color(0xFF451A03),
    frequentBorder = Color(0xFFF59E0B),
    standardText = Color(0xFFFB923C),
    standardBg = Color(0xFF431407),
    standardBorder = Color(0xFFF97316),
    extendedText = Color(0xFFC084FC),
    extendedBg = Color(0xFF3B0764),
    extendedBorder = Color(0xFFA855F7),
    customText = Color(0xFF2DD4BF),
    customBg = Color(0xFF042F2E),
    customBorder = Color(0xFF14B8A6),
    border = Color(0xFF334155),
    borderLight = Color(0xFF475569),
)

val EyeOff = Icons.Filled.VisibilityOff
val Eye = Icons.Filled.Visibility
val Lightbulb = Icons.Filled.Lightbulb

val BookOpen = Icons.Outlined.Book
val Sparkles = Icons.Filled.AutoAwesome
val Check = Icons.Filled.Check
val ChevronRight = Icons.Filled.ChevronRight
val RotateCcw = Icons.Filled.Refresh
val Home = Icons.Filled.Home

val H1TextStyle = TextStyle(
    fontSize = 30.sp,              // text-2xl (--text-2xl)
    fontWeight = FontWeight.Medium, // 500
    lineHeight = 45.sp             // 1.5 * fontSize
)

val H2TextStyle = TextStyle(
    fontSize = 20.sp,              // text-xl (--text-xl)
    fontWeight = FontWeight.Medium, // 500
    lineHeight = 30.sp             // 1.5 * fontSize
)

val H3TextStyle = TextStyle(
    fontSize = 18.sp,              // text-lg (--text-lg)
    fontWeight = FontWeight.Medium, // 500
    lineHeight = 27.sp             // 1.5 * fontSize
)

val BodyTextStyle = TextStyle(
    fontSize = 16.sp,              // text-base (--text-base)
    fontWeight = FontWeight.Normal, // 400
    lineHeight = 24.sp             // 1.5 * fontSize
)

val ButtonTextStyle = TextStyle(
    fontSize = 16.sp,              // text-base
    fontWeight = FontWeight.Medium, // 500
    lineHeight = 24.sp             // 1.5 * fontSize
)

val LabelTextStyle = TextStyle(
    fontSize = 16.sp,              // text-base
    fontWeight = FontWeight.Medium, // 500
    lineHeight = 24.sp             // 1.5 * fontSize
)

val SmallTextStyle = TextStyle(
    fontSize = 14.sp,              // text-sm
    fontWeight = FontWeight.Normal, // 400
    lineHeight = 21.sp             // 1.5 * fontSize
)

val PinyinTextStyle = TextStyle(
    fontSize = 24.sp,              // text-2xl
    fontWeight = FontWeight.Normal,
    lineHeight = 36.sp
)

val CharacterTextStyle = TextStyle(
    fontSize = 60.sp,              // text-6xl
    fontWeight = FontWeight.Normal,
    lineHeight = 90.sp
)

val CardCharacterTextStyle = TextStyle(
    fontSize = 30.sp,              // text-3xl
    fontWeight = FontWeight.Normal,
    lineHeight = 45.sp
)

val FontWeightNormal = FontWeight(400)  // --font-weight-normal
val FontWeightMedium = FontWeight(500)  // --font-weight-medium

val Padding4 = 4.dp
val Padding8 = 8.dp
val Padding12 = 12.dp
val Padding16 = 16.dp    // p-4
val Padding20 = 20.dp    // p-5
val Padding24 = 24.dp    // p-6
val Padding32 = 32.dp    // p-8

val RadiusSm = 6.dp      // rounded-lg
val RadiusMd = 8.dp      // rounded-xl
val RadiusLg = 10.dp     // rounded-2xl (--radius = 0.625rem = 10px)
val RadiusXl = 14.dp     // rounded-2xl for larger elements

val BorderWidth = 2.dp   // border-2

val IconSmall = 16.dp    // w-4 h-4
val IconMedium = 20.dp   // w-5 h-5
val IconLarge = 24.dp    // w-6 h-6
val IconXLarge = 32.dp   // w-8 h-8

val ButtonSmall = 32.dp   // h-8
val ButtonDefault = 48.dp // h-12
val ButtonLarge = 56.dp   // h-14

val IconContainer40 = 40.dp   // w-10 h-10
val IconContainer48 = 48.dp   // w-12 h-12
val IconContainer64 = 64.dp   // w-16 h-16

val backgroundGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFF0FDFA), // teal-50
        Color(0xFFECFDF5)  // emerald-50
    )
)

val buttonGradient = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF14B8A6), // teal-500
        Color(0xFF059669)  // emerald-600
    )
)

val buttonGradientPressed = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF0D9488), // teal-600
        Color(0xFF047857)  // emerald-700
    )
)

val cardGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFFFFFF), // white
        Color(0xFFF0FDFA)  // teal-50
    ),
    start = Offset(0f, 0f),
    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
)

val ElevationSmall = 2.dp      // shadow-sm
val ElevationMedium = 4.dp     // shadow-md
val ElevationLarge = 8.dp      // shadow-lg


@Composable
fun ProgressIndicator() {
    LinearProgressIndicator(
        progress = { 0.5f },
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp)),
        color = Color(0xFF14B8A6),          // teal-500
        trackColor = Color(0xFFE5E7EB)      // gray-200
    )
}