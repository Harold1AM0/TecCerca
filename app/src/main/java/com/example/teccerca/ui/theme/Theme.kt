package com.example.teccerca.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EsquemaColoresClaro = lightColorScheme(
    primary = ColorPrimario,
    secondary = ColorSecundario,
    background = Fondo
)

@Composable
fun TemaTecCerca(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaColoresClaro,
        typography = Tipografia,
        content = content
    )
}