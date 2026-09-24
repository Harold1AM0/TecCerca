package com.example.teccerca.ui.perfil

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.teccerca.ui.componentes.*

@Composable
fun PerfilClientePantalla(
    nombre: String, correo: String,
    onVolver: () -> Unit, onBuscar: () -> Unit, onCercanos: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Scaffold(
        topBar = { BarraSuperior("Perfil", onVolver) },
        bottomBar = { BarraInferior("Perfil", onBuscar, onCercanos, {}) }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)) {
            Surface(color = MaterialTheme.colorScheme.surface) {
                Column(Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    AvatarTecnico(nombre, grande = true)
                    Text(nombre, style = MaterialTheme.typography.titleLarge)
                    Text("Cliente TecCerca", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
                }
            }
            Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
                SeccionInformacion("INFORMACIÓN PERSONAL") {
                    FilaInformacion(Icons.Outlined.PersonOutline, "NOMBRE COMPLETO", nombre)
                    FilaInformacion(Icons.Outlined.Email, "CORREO ELECTRÓNICO", correo)
                    FilaInformacion(Icons.Outlined.Badge, "TIPO DE CUENTA", "Cliente", ultima = true)
                }
                Text("Estos son los datos asociados a tu cuenta.", style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                OutlinedButton(onClick = onCerrarSesion, modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp)) {
                    Text("Cerrar sesión")
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}
