package com.example.teccerca.ui.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.teccerca.data.respuesta.TecnicoRespuesta

@Composable
fun EstadoCliente(titulo: String, detalle: String, accion: String? = null, onAccion: () -> Unit = {}) {
    Surface(shape = RoundedCornerShape(24.dp), color = MaterialTheme.colorScheme.surfaceContainerLow) {
        Column(
            Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(Icons.Default.Search, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
            Text(titulo, style = MaterialTheme.typography.titleMedium)
            Text(detalle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            if (accion != null) OutlinedButton(onClick = onAccion) { Text(accion) }
        }
    }
}

fun LazyListScope.resultadosTecnicos(
    tecnicos: List<TecnicoRespuesta>,
    cargando: Boolean,
    mensaje: String,
    hayFiltros: Boolean,
    onReintentar: () -> Unit,
    onLimpiar: () -> Unit,
    onTecnico: (TecnicoRespuesta) -> Unit
) {
    when {
        cargando -> item {
            Column(Modifier.fillMaxWidth().padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                CircularProgressIndicator()
                Text("Buscando técnicos…")
            }
        }
        mensaje.isNotBlank() -> item {
            EstadoCliente("No pudimos cargar técnicos", mensaje, "Intentar nuevamente", onReintentar)
        }
        tecnicos.isEmpty() -> item {
            EstadoCliente(
                if (hayFiltros) "Sin resultados para tu búsqueda" else "No encontramos técnicos cercanos",
                if (hayFiltros) "Prueba con otro nombre o servicio." else "No hay técnicos disponibles en esta zona por ahora.",
                if (hayFiltros) "Limpiar filtros" else "Buscar nuevamente",
                if (hayFiltros) onLimpiar else onReintentar
            )
        }
        else -> items(tecnicos, key = { it.idTecnico }) { tecnico ->
            TarjetaTecnico(tecnico, onClick = { onTecnico(tecnico) })
        }
    }
}
