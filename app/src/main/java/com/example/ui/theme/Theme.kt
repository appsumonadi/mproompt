package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val OrangeColorScheme = darkColorScheme(
  primary = OrangePrimary,
  onPrimary = DarkBg,
  primaryContainer = OrangeDim,
  onPrimaryContainer = OrangeLight,
  secondary = OrangeLight,
  onSecondary = DarkBg,
  secondaryContainer = OrangeGlow,
  onSecondaryContainer = TextMain,
  tertiary = AccentCyan,
  onTertiary = TextMain,
  tertiaryContainer = DarkCardElevated,
  onTertiaryContainer = TextMain,
  background = DarkBg,
  onBackground = TextMain,
  surface = DarkCardBg,
  onSurface = TextMain,
  surfaceVariant = DarkCardElevated,
  onSurfaceVariant = TextMuted,
  outline = DarkCardBorder,
  outlineVariant = DarkBorderHighlight
)

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = OrangeColorScheme,
    typography = Typography,
    content = content
  )
}


