package com.example.teccerca.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.teccerca.data.respuesta.TecnicoRespuesta
import com.example.teccerca.ui.componentes.*

@Composable
fun DetalleTecnicoPantalla(tecnico: TecnicoRespuesta, onVolver: () -> Unit, onSolicitar: () -> Unit) {
    Scaffold(
        topBar = { BarraSuperior("Perfil técnico", onVolver) },
        bottomBar = {
            Surface(color = MaterialTheme.colorScheme.surface) {
                Column(Modifier.navigationBarsPadding().padding(horizontal = 20.dp, vertical = 16.dp)) {
                    BotonPrincipal("Solicitar servicio", onSolicitar)
                }
            }
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)) {
            Surface(color = MaterialTheme.colorScheme.surface) {
                Column(Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    AvatarTecnico(tecnico.nombre, grande = true)
                    Text(tecnico.nombre, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
                    Surface(shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.primaryContainer) {
                        Text("Disponible al realizar la búsqueda", Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    DatosTecnico(tecnico)
                }
            }
            Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
                SeccionInformacion("INFORMACIÓN PROFESIONAL") {
                    FilaInformacion(Icons.Outlined.PersonOutline, "NOMBRE COMPLETO", tecnico.nombre)
                    FilaInformacion(Icons.Outlined.Computer, "ESPECIALIDAD",
                        tecnico.especialidades.joinToString(" · ").ifBlank { "Servicio técnico" })
                    FilaInformacion(Icons.Outlined.WorkOutline, "EXPERIENCIA",
                        tecnico.experiencia?.let { "$it años" } ?: "No indicada")
                    FilaInformacion(Icons.Outlined.Call, "TELÉFONO", tecnico.telefono?.takeIf { it.isNotBlank() } ?: "No disponible", ultima = true)
                }
                SeccionInformacion("SOBRE EL TÉCNICO") {
                    Text(tecnico.descripcion?.takeIf { it.isNotBlank() } ?: "Aún no ha añadido una descripción.",
                        Modifier.padding(vertical = 16.dp), style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text("Solicita el servicio y describe tu problema. El técnico deberá aceptar la solicitud antes de confirmar la atención.",
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}
