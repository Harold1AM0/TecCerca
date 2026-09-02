package com.example.teccerca.ui.tecnico

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaGestionarSolicitudes() { // HU07 - Sprint 4[cite: 1]
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text(text = "Solicitudes Recibidas", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Cliente: Juan Pérez", style = MaterialTheme.typography.titleMedium)
                Text("Problema: Sin conexión a internet") // Información básica del servicio[cite: 1]
                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { /* Aceptar solicitud y actualizar estado */ }) { // Aceptar solicitud[cite: 1]
                        Text("Aceptar")
                    }
                    Button(onClick = { /* Rechazar solicitud y actualizar estado */ }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) { // Rechazar solicitud[cite: 1]
                        Text("Rechazar")
                    }
                }
            }
        }
    }
}