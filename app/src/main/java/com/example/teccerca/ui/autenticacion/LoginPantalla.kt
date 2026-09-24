package com.example.teccerca.ui.autenticacion

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.teccerca.data.respuesta.UsuarioRespuesta

@Composable
fun LoginPantalla(
    viewModel: AutenticacionViewModel = viewModel(),
    irCliente: (UsuarioRespuesta) -> Unit, irTecnico: () -> Unit, irRegistro: () -> Unit,
    correoInicial: String = ""
) {
    var correo by rememberSaveable { mutableStateOf(correoInicial) }
    LaunchedEffect(correoInicial) { if (correoInicial.isNotBlank()) correo = correoInicial }
    var password by remember { mutableStateOf("") }
    var mostrarPassword by remember { mutableStateOf(false) }
    val mensaje by viewModel.mensaje
    val cargando by viewModel.cargando

    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(Modifier.safeDrawingPadding().imePadding().verticalScroll(rememberScrollState())) {
            Column(
                Modifier.fillMaxWidth().padding(top = 48.dp, bottom = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(shape = RoundedCornerShape(22.dp), color = MaterialTheme.colorScheme.primary, shadowElevation = 4.dp) {
                    Icon(Icons.Outlined.Build, null, Modifier.padding(16.dp).size(32.dp), tint = MaterialTheme.colorScheme.onPrimary)
                }
                Text("TecCerca", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)
                Text("Encuentra técnicos cerca de ti", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Surface(shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp), color = MaterialTheme.colorScheme.surface) {
                Column(Modifier.fillMaxWidth().padding(24.dp), verticalArrangement = Arrangement.spacedBy(22.dp)) {
                    Text("Ingresa a tu cuenta", style = MaterialTheme.typography.titleLarge)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Correo electrónico", style = MaterialTheme.typography.labelMedium)
                        OutlinedTextField(
                            value = correo, onValueChange = { correo = it },
                            placeholder = { Text("ejemplo@correo.com", style = MaterialTheme.typography.bodyMedium) },
                            leadingIcon = { Icon(Icons.Outlined.Email, null) },
                            singleLine = true, enabled = !cargando, modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Correo electrónico" },
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
                        )
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Contraseña", style = MaterialTheme.typography.labelMedium)
                        OutlinedTextField(
                            value = password, onValueChange = { password = it }, placeholder = { Text("••••••••") },
                            leadingIcon = { Icon(Icons.Outlined.Lock, null) },
                            trailingIcon = {
                                IconButton(onClick = { mostrarPassword = !mostrarPassword }) {
                                    Icon(if (mostrarPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                        if (mostrarPassword) "Ocultar contraseña" else "Mostrar contraseña")
                                }
                            },
                            singleLine = true, enabled = !cargando, modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Contraseña" },
                            shape = RoundedCornerShape(12.dp),
                            visualTransformation = if (mostrarPassword) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                    }
                    if (mensaje.isNotBlank()) Text(mensaje, color = MaterialTheme.colorScheme.error)
                    Button(
                        onClick = {
                            viewModel.iniciarSesion(correo, password) { rol ->
                                when (rol) {
                                    1 -> viewModel.usuario.value?.let(irCliente)
                                    2 -> irTecnico()
                                }
                            }
                        },
                        enabled = !cargando && correo.isNotBlank() && password.isNotBlank(),
                        modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp), shape = RoundedCornerShape(12.dp)
                    ) {
                        if (cargando) CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
                        Text(if (cargando) " Ingresando…" else "Iniciar sesión")
                        if (!cargando) Icon(Icons.AutoMirrored.Filled.ArrowForward, null, Modifier.padding(start = 8.dp).size(24.dp))
                    }
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Text("¿No tienes una cuenta?", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        TextButton(onClick = irRegistro, enabled = !cargando) { Text("Registrarse", style = MaterialTheme.typography.labelMedium) }
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
