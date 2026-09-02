package com.example.teccerca.ui.autenticacion

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaLogin() { // HU01 - Inicio de sesión[cite: 1]
    var correo by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(text = "Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Lógica de autenticación */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }
    }
}