package com.example.teccerca.ui.autenticacion

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tipo de archivo: Kotlin file con una función @Composable de nivel superior
private val TealPrimary = Color(0xFF009688)

@Composable
fun PantallaRegistro(
    onRegistrar: (rol: String, nombre: String, apellidos: String, correo: String, contrasena: String, edad: String, especialidad: String?) -> Unit,
    onVolver: () -> Unit,
    mensajeError: String? = null
) {
    var rol by remember { mutableStateOf("Cliente") }
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("") }
    var mostrarContrasena by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVolver) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Text("Crear cuenta", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(64.dp).background(TealPrimary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.PersonAdd, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Selector de rol
            Text("¿Cómo quieres registrarte?", fontSize = 13.sp, color = Color.Gray, modifier = Modifier.align(Alignment.Start))
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { rol = "Cliente" }
                ) {
                    RadioButton(selected = rol == "Cliente", onClick = { rol = "Cliente" })
                    Text("Cliente")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { rol = "Tecnico" }
                ) {
                    RadioButton(selected = rol == "Tecnico", onClick = { rol = "Tecnico" })
                    Text("Técnico")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = nombre, onValueChange = { nombre = it },
                label = { Text("Nombre") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                singleLine = true, modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = apellidos, onValueChange = { apellidos = it },
                label = { Text("Apellidos") },
                leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null) },
                singleLine = true, modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = correo, onValueChange = { correo = it },
                label = { Text("Correo Electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true, modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = edad, onValueChange = { edad = it.filter { c -> c.isDigit() } },
                label = { Text("Edad") },
                leadingIcon = { Icon(Icons.Default.Cake, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true, modifier = Modifier.fillMaxWidth()
            )

            // Solo aparece si eligió "Técnico"
            if (rol == "Tecnico") {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = especialidad, onValueChange = { especialidad = it },
                    label = { Text("Especialidad") },
                    leadingIcon = { Icon(Icons.Default.Build, contentDescription = null) },
                    placeholder = { Text("Ej. Reparación de Computadoras") },
                    singleLine = true, modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = contrasena, onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { mostrarContrasena = !mostrarContrasena }) {
                        Icon(
                            imageVector = if (mostrarContrasena) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (mostrarContrasena) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true, modifier = Modifier.fillMaxWidth()
            )

            if (mensajeError != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(mensajeError, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    onRegistrar(rol, nombre, apellidos, correo, contrasena, edad, if (rol == "Tecnico") especialidad else null)
                },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
            ) {
                Text("Crear cuenta")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text("¿Ya tienes cuenta? ", fontSize = 13.sp, color = Color.Gray)
                Text(
                    "Inicia sesión", fontSize = 13.sp, color = TealPrimary, fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onVolver() }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}