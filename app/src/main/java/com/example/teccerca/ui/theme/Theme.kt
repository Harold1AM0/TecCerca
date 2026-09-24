package com.example.teccerca.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Tipo de archivo: Kotlin file con una función @Composable de nivel superior (envuelve toda la app)

private val EsquemaClaro = lightColorScheme(
    primary = TealPrimary,
    onPrimary = FondoClaro,
    primaryContainer = TealContainer,
    onPrimaryContainer = TealPrimaryDark,
    secondary = androidx.compose.ui.graphics.Color(0xFF21734B),
    secondaryContainer = androidx.compose.ui.graphics.Color(0xFFD9F5E4),
    onSecondaryContainer = androidx.compose.ui.graphics.Color(0xFF12462D),
    tertiary = androidx.compose.ui.graphics.Color(0xFF986600),
    background = SuperficieClara,
    onBackground = TextoPrimarioClaro,
    surface = FondoClaro,
    onSurface = TextoPrimarioClaro,
    surfaceVariant = SuperficieClara,
    surfaceContainerLow = SuperficieClara,
    surfaceContainer = SuperficieClara,
    outlineVariant = BordeClaro,
    onSurfaceVariant = TextoSecundarioClaro,
    outline = BordeClaro,
    error = RojoError
)

private val EsquemaOscuro = darkColorScheme(
    primary = TealPrimaryLight,
    onPrimary = FondoOscuro,
    primaryContainer = TealPrimaryDark,
    onPrimaryContainer = TealContainer,
    secondary = androidx.compose.ui.graphics.Color(0xFF91D5AE),
    background = FondoOscuro,
    onBackground = TextoPrimarioOscuro,
    surface = SuperficieOscura,
    onSurface = TextoPrimarioOscuro,
    surfaceVariant = SuperficieOscura,
    surfaceContainerLow = SuperficieOscura,
    surfaceContainer = SuperficieOscura,
    outlineVariant = BordeOscuro,
    onSurfaceVariant = TextoSecundarioOscuro,
    outline = BordeOscuro,
    error = RojoError
)

@Composable
fun TecCercaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // En Android 12+ se puede usar color dinámico del wallpaper; lo dejamos apagado
    // por defecto para mantener siempre la identidad de marca (teal) de TecCerca.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> EsquemaOscuro
        else -> EsquemaClaro
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}