package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = Navy,
    onPrimary = Color.White,
    secondary = Green,
    onSecondary = Color.White,
    tertiary = Teal,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    error = Amber
  )

private val LightColorScheme =
  lightColorScheme(
    primary = Navy,
    onPrimary = Color.White,
    secondary = Green,
    onSecondary = Color.White,
    tertiary = Teal,
    background = OffWhite,
    surface = Color.White,
    onBackground = TextDark,
    onSurface = TextDark,
    error = Amber
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color disabled to ensure consistent branding
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
