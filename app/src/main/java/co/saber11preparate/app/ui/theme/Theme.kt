package co.saber11preparate.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = BrandBlue,
    onPrimary = Color.White,
    primaryContainer = BrandBlueLight.copy(alpha = 0.25f),
    onPrimaryContainer = BrandBlue,
    secondary = BrandGreen,
    onSecondary = Color.White,
    secondaryContainer = BrandGreenLight.copy(alpha = 0.25f),
    onSecondaryContainer = BrandGreen,
    tertiary = BrandAccent,
    onTertiary = Color.White,
    tertiaryContainer = BrandAccentLight.copy(alpha = 0.3f),
    onTertiaryContainer = BrandAccent,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnBackground,
    surfaceVariant = Color(0xFFE8EDF3),
    onSurfaceVariant = Color(0xFF424A55),
    outline = LightOutline,
)

private val DarkColorScheme = darkColorScheme(
    primary = BrandBlueLight,
    onPrimary = Color(0xFF002544),
    primaryContainer = BrandBlue,
    onPrimaryContainer = Color.White,
    secondary = BrandGreenLight,
    onSecondary = Color(0xFF003822),
    secondaryContainer = BrandGreen,
    onSecondaryContainer = Color.White,
    tertiary = BrandAccentLight,
    onTertiary = Color(0xFF3A1800),
    tertiaryContainer = BrandAccent,
    onTertiaryContainer = Color.White,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnBackground,
    surfaceVariant = Color(0xFF2A313C),
    onSurfaceVariant = Color(0xFFC2C8D1),
    outline = DarkOutline,
)

@Composable
fun Saber11Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
