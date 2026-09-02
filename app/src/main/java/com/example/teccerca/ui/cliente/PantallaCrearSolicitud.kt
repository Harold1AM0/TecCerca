package com.example.teccerca.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaCrearSolicitud() { // HU06 - Sprint 3[cite: 1]
    var descripcion by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text(text = "Generar Solicitud de Servicio", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción del problema o necesidad") }, // Descripción del problema[cite: 1]
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = ubicacion,
            onValueChange = { ubicacion = it },
            label = { Text("Ubicación del servicio") }, // Ubicación del servicio[cite: 1]
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Lógica para enviar solicitud con estado Pendiente */ }, // Inicia con estado Pendiente[cite: 1]
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar Solicitud")
        }
    }
}