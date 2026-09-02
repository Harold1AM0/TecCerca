package com.example.teccerca.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaBuscarCategoria() { // HU03 - Sprint 2[cite: 1]
    val categorias = listOf(
        "Computadoras y laptops",
        "Redes y Wi-Fi",
        "Electricidad",
        "Celulares",
        "Electrodomésticos"
    ) // Categorías iniciales[cite: 1]

    var categoriaSeleccionada by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text(text = "Buscar por Categoría", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        categorias.forEach { categoria ->
            Button(
                onClick = { categoriaSeleccionada = categoria },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Text(categoria)
            }
        }
    }
}