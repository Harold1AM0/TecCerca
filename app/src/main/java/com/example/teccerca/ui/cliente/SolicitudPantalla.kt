package com.example.teccerca.ui.cliente

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.teccerca.ui.componentes.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolicitudPantalla(
    idTecnico: Int,
    nombreTecnico: String,
    idCliente: Int?,
    onVolver: () -> Unit,
    onConfirmada: (Int) -> Unit,
    solicitudViewModel: SolicitudViewModel = viewModel()
) {
    var descripcion by rememberSaveable { mutableStateOf("") }
    val cargando by solicitudViewModel.cargando
    val mensaje by solicitudViewModel.mensaje
    val idSolicitud by solicitudViewModel.idSolicitud
    LaunchedEffect(idSolicitud) { idSolicitud?.let(onConfirmada) }
    BackHandler(enabled = cargando) { /* Esperar la respuesta evita abandonar un envío en curso. */ }

    Scaffold(topBar = { BarraSuperior("Solicitar servicio", if (cargando) null else onVolver)
    }) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).imePadding().verticalScroll(rememberScrollState()).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text("Cuéntanos qué pasó", style = MaterialTheme.typography.headlineMedium)
            Text("Un poco de detalle ayudará al técnico a entender lo que necesitas.",
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Row(Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AvatarTecnico(nombreTecnico)
                    Column {
                        Text("Tu técnico", style = MaterialTheme.typography.labelMedium)
                        Text(nombreTecnico, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                enabled = !cargando,
                label = { Text("Describe tu problema") },
                placeholder = { Text("Por ejemplo: mi laptop enciende, pero la pantalla se queda en negro…") },
                minLines = 5,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                supportingText = { Text("Incluye el equipo, la falla y desde cuándo ocurre.") }
            )
            if (idCliente == null) {
                EstadoCliente("Tu cuenta necesita vincularse", "Aún no podemos enviar solicitudes desde esta cuenta. Contacta con el equipo de TecCerca.")
            }
            if (mensaje.isNotEmpty() && idSolicitud == null) {
                Text(mensaje, color = MaterialTheme.colorScheme.error)
            }
            BotonPrincipal(
                "Enviar solicitud",
                onClick = { idCliente?.let { solicitudViewModel.crearSolicitud(it, idTecnico, descripcion) } },
                habilitado = descripcion.isNotBlank() && idCliente != null && idSolicitud == null,
                cargando = cargando
            )
            Text(
                if (cargando) "Estamos enviando tu solicitud. Espera la confirmación."
                else "El envío no confirma una visita ni realiza un cobro. El técnico revisará tu solicitud.",
                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ConfirmacionSolicitudPantalla(nombreTecnico: String, idSolicitud: Int, onInicio: () -> Unit) {
    BackHandler(onBack = onInicio)
    Surface(Modifier.fillMaxSize()) {
        Column(
            Modifier.safeDrawingPadding().verticalScroll(rememberScrollState()).padding(28.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(Modifier.height(32.dp))
            Surface(shape = RoundedCornerShape(24.dp), color = MaterialTheme.colorScheme.secondaryContainer) {
                Text("✓", Modifier.padding(28.dp), style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer)
            }
            Text("Solicitud enviada\ncorrectamente", style = MaterialTheme.typography.headlineLarge)
            Text("$nombreTecnico revisará tu solicitud.", style = MaterialTheme.typography.titleMedium)
            Text("Solicitud #$idSolicitud · Pendiente de revisión", color = MaterialTheme.colorScheme.secondary)
            Text("Tu problema ya está registrado. La solicitud aún debe ser aceptada por el técnico.",
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            BotonPrincipal("Volver al inicio", onInicio)
        }
    }
}
