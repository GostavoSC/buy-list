package com.gstv.buylist.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color


private val lightScheme = lightColorScheme(
    primary = BuyListColors.Dark,
    background = BuyListColors.Primary,
//    onPrimary = ColorLightTokens.OnPrimary,
//    primaryContainer = ColorLightTokens.PrimaryContainer,
//    onPrimaryContainer = ColorLightTokens.OnPrimaryContainer,
//    secondary = ColorLightTokens.Secondary,
//    onSecondary = ColorLightTokens.OnSecondary,
//    secondaryContainer = ColorLightTokens.SecondaryContainer,
//    onSecondaryContainer = ColorLightTokens.OnSecondaryContainer,
//    tertiary = ColorLightTokens.Tertiary,
//    onTertiary = ColorLightTokens.OnTertiary,
//    tertiaryContainer = ColorLightTokens.TertiaryContainer,
//    onTertiaryContainer = ColorLightTokens.OnTertiaryContainer,
//    error = ColorLightTokens.Error,
//    onError = ColorLightTokens.OnError,
//    errorContainer = ColorLightTokens.ErrorContainer,
//    onErrorContainer = ColorLightTokens.OnErrorContainer,
//    background = ColorLightTokens.Background,
//    onBackground = ColorLightTokens.OnBackground,
    surface = Color.White,
//    onSurface = ColorLightTokens.OnSurface,
//    surfaceVariant = ColorLightTokens.SurfaceVariant,
//    onSurfaceVariant = ColorLightTokens.OnSurfaceVariant,
//    outline = ColorLightTokens.Outline,
//    outlineVariant = ColorLightTokens.OutlineVariant,
//    scrim = ColorLightTokens.Scrim,
//    inverseSurface = ColorLightTokens.InverseSurface,
//    inverseOnSurface = ColorLightTokens.InverseOnSurface,
//    inversePrimary = ColorLightTokens.InversePrimary,
//    surfaceDim = ColorLightTokens.SurfaceDim,
//    surfaceBright = ColorLightTokens.SurfaceBright,
//    surfaceContainerLowest = ColorLightTokens.SurfaceContainerLowest,
//    surfaceContainerLow = ColorLightTokens.SurfaceContainerLow,
//    surfaceContainer = ColorLightTokens.SurfaceContainer,
//    surfaceContainerHigh = ColorLightTokens.SurfaceContainerHigh,
//    surfaceContainerHighest = ColorLightTokens.SurfaceContainerHighest
)

private val darkScheme = darkColorScheme(
//    primary = ColorLightTokens.Primary,
//    onPrimary = ColorLightTokens.OnPrimary,
//    primaryContainer = ColorLightTokens.PrimaryContainer,
//    onPrimaryContainer = ColorLightTokens.OnPrimaryContainer,
//    secondary = ColorLightTokens.Secondary,
//    onSecondary = ColorLightTokens.OnSecondary,
//    secondaryContainer = ColorLightTokens.SecondaryContainer,
//    onSecondaryContainer = ColorLightTokens.OnSecondaryContainer,
//    tertiary = ColorLightTokens.Tertiary,
//    onTertiary = ColorLightTokens.OnTertiary,
//    tertiaryContainer = ColorLightTokens.TertiaryContainer,
//    onTertiaryContainer = ColorLightTokens.OnTertiaryContainer,
//    error = ColorLightTokens.Error,
//    onError = ColorLightTokens.OnError,
//    errorContainer = ColorLightTokens.ErrorContainer,
//    onErrorContainer = ColorLightTokens.OnErrorContainer,
//    background = ColorLightTokens.Background,
//    onBackground = ColorLightTokens.OnBackground,
//    surface = ColorLightTokens.Surface,
//    onSurface = ColorLightTokens.OnSurface,
//    surfaceVariant = ColorLightTokens.SurfaceVariant,
//    onSurfaceVariant = ColorLightTokens.OnSurfaceVariant,
//    outline = ColorLightTokens.Outline,
//    outlineVariant = ColorLightTokens.OutlineVariant,
//    scrim = ColorLightTokens.Scrim,
//    inverseSurface = ColorLightTokens.InverseSurface,
//    inverseOnSurface = ColorLightTokens.InverseOnSurface,
//    inversePrimary = ColorLightTokens.InversePrimary,
//    surfaceDim = ColorLightTokens.SurfaceDim,
//    surfaceBright = ColorLightTokens.SurfaceBright,
//    surfaceContainerLowest = ColorLightTokens.SurfaceContainerLowest,
//    surfaceContainerLow = ColorLightTokens.SurfaceContainerLow,
//    surfaceContainer = ColorLightTokens.SurfaceContainer,
//    surfaceContainerHigh = ColorLightTokens.SurfaceContainerHigh,
//    surfaceContainerHighest = ColorLightTokens.SurfaceContainerHighest
)

@Composable
fun BuyListTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    val colorScheme = when {
        darkTheme -> darkScheme
        else -> lightScheme
    }

    CompositionLocalProvider(
        LocalDarkMode provides darkTheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = MaterialTheme.typography,
            shapes = MaterialTheme.shapes,
            content = content
        )
    }
}

val LocalDarkMode = compositionLocalOf(structuralEqualityPolicy()) { false }