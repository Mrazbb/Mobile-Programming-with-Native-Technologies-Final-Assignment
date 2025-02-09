package com.example.finalassignment.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// A more vibrant dark theme color scheme:
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),     // A bright purple
    secondary = Color(0xFF03DAC6),   // A vivid teal
    tertiary = Color(0xFFCF6679),    // A vibrant pink-red
    background = Color(0xFF121212),  // Dark background
    surface = Color(0xFF1F1B24),     // Slightly lighter surface for contrast
    error = Color(0xFFCF6679),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black
)

// A more vibrant light theme color scheme:
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),     // A vivid purple
    secondary = Color(0xFF03DAC6),   // A vibrant teal
    tertiary = Color(0xFFB00020),    // A bright red
    background = Color(0xFFFFFBFE),  // Off-white background
    surface = Color(0xFFFFFBFE),     // Same as background for a clean look
    error = Color(0xFFB00020),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    onError = Color.White
)

@Composable
fun FinalAssignmentTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}