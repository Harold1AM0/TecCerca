package com.example.teccerca.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaPerfilTecnico() { // HU05 - Sprint 3[cite: 1]
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text(text = "Perfil del Técnico", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Especialidad: Redes y Wi-Fi", style = MaterialTheme.typography.bodyLarge) // Muestra especialidad[cite: 1]
        Text(text = "Experiencia: 5 años", style = MaterialTheme.typography.bodyLarge) // Muestra experiencia[cite: 1]
        Text(text = "Calificación Promedio: 4.8/5", style = MaterialTheme.typography.bodyLarge) // Muestra calificación[cite: 1]

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Opiniones de servicios realizados:", style = MaterialTheme.typography.titleMedium) // Muestra opiniones[cite: 1]

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Navegar a crear solicitud */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Solicitar Servicio")
        }
    }
}