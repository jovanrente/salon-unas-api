package com.salonunas.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SalonColors = lightColorScheme(
    primary = Color(0xFFD81B60),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFD9E2),
    onPrimaryContainer = Color(0xFF3F0017),
    secondary = Color(0xFF8E4585),
    onSecondary = Color.White,
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE)
)

@Composable
fun SalonTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SalonColors,
        content = content
    )
}
