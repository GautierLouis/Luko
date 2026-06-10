package xyz.luko.designsystem.token.typo

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import luko.library.feature.ui.design_system.generated.resources.Res
import luko.library.feature.ui.design_system.generated.resources.openhuninn
import org.jetbrains.compose.resources.Font

@Composable
fun openHuninn(): Typography {
    val openHuninn = FontFamily(
        Font(Res.font.openhuninn, androidx.compose.ui.text.font.FontWeight.Normal),
    )

    return with(MaterialTheme.typography) {
        copy(
            displayLarge = displayLarge.copy(fontFamily = openHuninn),
            displayMedium = displayMedium.copy(fontFamily = openHuninn),
            displaySmall = displaySmall.copy(fontFamily = openHuninn),
            headlineLarge = headlineLarge.copy(fontFamily = openHuninn),
            headlineMedium = headlineMedium.copy(fontFamily = openHuninn),
            headlineSmall = headlineSmall.copy(fontFamily = openHuninn),
            titleLarge = titleLarge.copy(fontFamily = openHuninn),
            titleMedium = titleMedium.copy(fontFamily = openHuninn),
            titleSmall = titleSmall.copy(fontFamily = openHuninn),
            bodyLarge = bodyLarge.copy(fontFamily = openHuninn),
            bodyMedium = bodyMedium.copy(fontFamily = openHuninn),
            bodySmall = bodySmall.copy(fontFamily = openHuninn),
            labelLarge = labelLarge.copy(fontFamily = openHuninn),
            labelMedium = labelMedium.copy(fontFamily = openHuninn),
            labelSmall = labelSmall.copy(fontFamily = openHuninn),
        )
    }
}
