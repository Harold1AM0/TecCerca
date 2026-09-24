package com.example.teccerca.ui.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.teccerca.data.respuesta.TecnicoRespuesta
import java.util.Locale

@Composable
fun AvatarTecnico(nombre: String, grande: Boolean = false) {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        border = BorderStroke(if (grande) 3.dp else 2.dp, MaterialTheme.colorScheme.primaryContainer),
        modifier = Modifier.size(if (grande) 88.dp else 52.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(nombre.split(" ").filter { it.isNotBlank() }.take(2).map { it.first() }.joinToString("").uppercase(),
                style = if (grande) MaterialTheme.typography.headlineLarge else MaterialTheme.typography.titleLarge)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DatosTecnico(tecnico: TecnicoRespuesta) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Star, null, Modifier.size(16.dp), tint = MaterialTheme.colorScheme.tertiary)
            Spacer(Modifier.width(4.dp))
            Text(tecnico.puntajePromedio?.toDoubleOrNull()?.let { String.format(Locale.getDefault(), "%.1f", it) } ?: "Sin valoración",
                style = MaterialTheme.typography.labelMedium)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.LocationOn, null, Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(String.format(Locale.getDefault(), "%.2f km", tecnico.distanciaKm),
                style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun TarjetaTecnico(tecnico: TecnicoRespuesta, onClick: () -> Unit) {
    OutlinedCard(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AvatarTecnico(tecnico.nombre)
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(tecnico.nombre, style = MaterialTheme.typography.titleSmall)
                Text(tecnico.especialidades.joinToString(" · ").ifBlank { "Servicio técnico" },
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                DatosTecnico(tecnico)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "Ver perfil", tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
