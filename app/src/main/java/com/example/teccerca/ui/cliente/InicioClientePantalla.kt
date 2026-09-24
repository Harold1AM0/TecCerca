package com.example.teccerca.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.teccerca.data.respuesta.TecnicoRespuesta
import com.example.teccerca.ui.componentes.*

@Composable
fun InicioClientePantalla(
    nombre: String, clienteViewModel: ClienteViewModel,
    onBuscar: () -> Unit, onTecnico: (TecnicoRespuesta) -> Unit,
    onPerfil: () -> Unit, onNotificaciones: () -> Unit
) {
    val buscando = clienteViewModel.consulta.value.isNotBlank()
    val tecnicos = clienteViewModel.resultados
    val cargando = clienteViewModel.cargando.value
    val mensaje = clienteViewModel.mensaje.value
    Scaffold(
        topBar = {
            BarraSuperior("Buscar técnicos", acciones = {
                IconButton(onClick = onNotificaciones) { Icon(Icons.Outlined.NotificationsNone, "Notificaciones") }
            })
        },
        bottomBar = { BarraInferior("Buscar", {}, onBuscar, onPerfil) }
    ) { padding ->
        LazyColumn(
            Modifier.fillMaxSize().padding(padding).testTag("home"),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Hola, ${nombre.substringBefore(" ").ifBlank { "bienvenido" }} 👋",
                        style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                    Text("¿Qué necesitas reparar hoy?", style = MaterialTheme.typography.titleLarge)
                    Text("Encuentra expertos cerca de tu ubicación.", style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            item { BarraBusqueda(clienteViewModel.consulta.value, clienteViewModel::buscar, onBuscar) }
            if (!buscando) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Categorías de servicio", Modifier.weight(1f), style = MaterialTheme.typography.titleSmall)
                        TextButton(onClick = { clienteViewModel.seleccionarCategoria(null); onBuscar() }) { Text("Ver todas") }
                    }
                }
                items(clienteViewModel.categorias.value.chunked(2)) { fila ->
                    Row(horizontalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.fillMaxWidth()) {
                        fila.forEach { categoria ->
                            Box(Modifier.weight(1f)) {
                                CategoriaCard(
                                    categoria.nombre, clienteViewModel.categoria.value == categoria.nombre,
                                    cantidad = if (cargando || mensaje.isNotEmpty()) null
                                        else clienteViewModel.tecnicos.value.count { categoria.nombre in it.especialidades }
                                ) {
                                    clienteViewModel.seleccionarCategoria(categoria.nombre)
                                    onBuscar()
                                }
                            }
                        }
                        if (fila.size == 1) Spacer(Modifier.weight(1f))
                    }
                }
            }
            item { UbicacionCliente(clienteViewModel) }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(if (buscando) "Resultados de búsqueda" else "Técnicos a tu alrededor",
                        Modifier.weight(1f), style = MaterialTheme.typography.titleSmall)
                    if (!cargando && mensaje.isEmpty()) Text(if (tecnicos.size == 1) "1 resultado" else "${tecnicos.size} resultados",
                        style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            resultadosTecnicos(
                tecnicos.take(3), cargando, mensaje,
                buscando || clienteViewModel.categoria.value != null,
                clienteViewModel::recargar, clienteViewModel::limpiarFiltros, onTecnico
            )
            if (tecnicos.size > 3) item { TextButton(onClick = onBuscar) { Text("Ver todos los técnicos") } }
        }
    }
}
