package com.example.teccerca.ui.autenticacion

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaRegistroTecnico() { // HU02 - Registro del perfil técnico[cite: 1]
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("") }
    var experiencia by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(text = "Registro de Técnico", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = contraseña,
            onValueChange = { contraseña = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = especialidad,
            onValueChange = { especialidad = it },
            label = { Text("Especialidad (ej. Electricidad, Redes)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = experiencia,
            onValueChange = { experiencia = it },
            label = { Text("Años de experiencia") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Lógica de registro técnico */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse como Técnico")
        }
    }
}