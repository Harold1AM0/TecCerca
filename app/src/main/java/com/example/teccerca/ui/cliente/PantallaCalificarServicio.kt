package com.example.teccerca.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaCalificarServicio() { // HU08 - Sprint 4[cite: 1]
    var calificacion by remember { mutableStateOf("") }
    var comentario by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text(text = "Calificar Servicio Finalizado", style = MaterialTheme.typography.headlineMedium) // Restricción a servicios finalizados[cite: 1]
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = calificacion,
            onValueChange = { calificacion = it },
            label = { Text("Puntuación (1 al 5)") }, // Asignar puntuación[cite: 1]
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = comentario,
            onValueChange = { comentario = it },
            label = { Text("Comentario sobre la atención") }, // Registrar comentario[cite: 1]
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Lógica para actualizar reputación del técnico */ }, // Incorporar al promedio del técnico[cite: 1]
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar Calificación")
        }
    }
}