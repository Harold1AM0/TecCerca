package com.example.teccerca.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.teccerca.data.respuesta.TecnicoRespuesta
import com.example.teccerca.ui.componentes.*

@Composable
fun TecnicosCercanosPantalla(
    clienteViewModel: ClienteViewModel,
    onVolver: () -> Unit, onTecnico: (TecnicoRespuesta) -> Unit,
    onPerfil: () -> Unit = {}
) {
    var mejorValorados by rememberSaveable { mutableStateOf(false) }
    val resultados = if (mejorValorados) clienteViewModel.resultados.sortedByDescending { it.puntajePromedio?.toDoubleOrNull() ?: -1.0 }
        else clienteViewModel.resultados.sortedBy { it.distanciaKm }
    Scaffold(
        topBar = { BarraSuperior("Técnicos cercanos", onVolver) },
        bottomBar = { BarraInferior("Cercanos", onVolver, {}, onPerfil) }
    ) { padding ->
        LazyColumn(
            Modifier.fillMaxSize().padding(padding).testTag("lista"),
            contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.primaryContainer) {
                    Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Outlined.LocationOn, null, tint = MaterialTheme.colorScheme.primary)
                            Text("Encuentra ayuda cerca de ti", style = MaterialTheme.typography.titleSmall)
                        }
                        UbicacionCliente(clienteViewModel)
                    }
                }
            }
            item { BarraBusqueda(clienteViewModel.consulta.value, clienteViewModel::buscar) }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item { FilterChip(selected = clienteViewModel.categoria.value == null,
                        onClick = { clienteViewModel.seleccionarCategoria(null) }, label = { Text("Todos") }) }
                    items(clienteViewModel.categorias.value, key = { it.idEspecialidad }) { categoria ->
                        FilterChip(selected = clienteViewModel.categoria.value == categoria.nombre,
                            onClick = { clienteViewModel.seleccionarCategoria(categoria.nombre) }, label = { Text(categoria.nombre) })
                    }
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(selected = !mejorValorados, onClick = { mejorValorados = false }, label = { Text("Más cercanos") })
                    FilterChip(selected = mejorValorados, onClick = { mejorValorados = true }, label = { Text("Mejor valorados") })
                }
            }
            if (!clienteViewModel.cargando.value && clienteViewModel.mensaje.value.isEmpty()) item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Técnicos a tu alrededor", Modifier.weight(1f), style = MaterialTheme.typography.titleSmall)
                    Text(if (resultados.size == 1) "1 resultado" else "${resultados.size} resultados", style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            resultadosTecnicos(resultados, clienteViewModel.cargando.value, clienteViewModel.mensaje.value,
                clienteViewModel.consulta.value.isNotBlank() || clienteViewModel.categoria.value != null,
                clienteViewModel::recargar, clienteViewModel::limpiarFiltros, onTecnico)
        }
    }
}
