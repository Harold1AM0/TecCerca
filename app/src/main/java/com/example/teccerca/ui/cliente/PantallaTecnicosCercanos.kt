package com.example.teccerca.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaTecnicosCercanos() { // HU04 - Sprint 2[cite: 1]
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text(text = "Técnicos Cercanos", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Nombre del Técnico", style = MaterialTheme.typography.titleMedium)
                Text("Especialidad: Redes y Wi-Fi")
                Text("Distancia: 2.5 km") // Referencia de distancia[cite: 1]
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { /* Lógica para ver perfil */ }) {
                    Text("Ver Perfil")
                }
            }
        }
    }
}