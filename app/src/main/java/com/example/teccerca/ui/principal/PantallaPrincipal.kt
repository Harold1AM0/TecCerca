package com.example.teccerca.ui.principal

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.teccerca.data.modelo.PerfilTecnico
import com.example.teccerca.data.modelo.Usuario
import com.example.teccerca.ui.cliente.PantallaPerfilCliente
import com.example.teccerca.ui.tecnico.PantallaPerfilTecnico

// Tipo de archivo: Kotlin file con un enum + una función @Composable de nivel superior

private enum class TabPrincipal { BUSCAR, MAPA, PERFIL }

@Composable
fun PantallaPrincipal(
    usuario: Usuario,
    onCerrarSesion: () -> Unit
) {
    var tabSeleccionado by remember { mutableStateOf(TabPrincipal.BUSCAR) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = tabSeleccionado == TabPrincipal.BUSCAR,
                    onClick = { tabSeleccionado = TabPrincipal.BUSCAR },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                    label = { Text("Buscar") }
                )
                NavigationBarItem(
                    selected = tabSeleccionado == TabPrincipal.MAPA,
                    onClick = { tabSeleccionado = TabPrincipal.MAPA },
                    icon = { Icon(Icons.Default.Map, contentDescription = "Mapa") },
                    label = { Text("Mapa") }
                )
                NavigationBarItem(
                    selected = tabSeleccionado == TabPrincipal.PERFIL,
                    onClick = { tabSeleccionado = TabPrincipal.PERFIL },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { paddingInterno ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingInterno)) {
            when (tabSeleccionado) {
                TabPrincipal.BUSCAR -> PantallaPlaceholder("Buscar técnicos") // TODO: PantallaBuscarCategoria
                TabPrincipal.MAPA -> PantallaPlaceholder("Mapa de técnicos cercanos") // TODO: PantallaTecnicosCercanos + AsistenteUbicacion
                TabPrincipal.PERFIL -> {
                    if (usuario.rol == "Tecnico") {
                        PantallaPerfilTecnico(
                            perfil = PerfilTecnico(
                                nombre = usuario.nombre,
                                apellido = usuario.apellidos,
                                edad = usuario.edad.toIntOrNull() ?: 0,
                                correoElectronico = usuario.correo,
                                especialidad = usuario.especialidad ?: "",
                                calificacionPromedio = 0.0, // TODO: calcular con reseñas reales
                                numeroOpiniones = 0,
                                estaActivo = true,
                                esEspecialistaVerificado = false
                            ),
                            onVolver = onCerrarSesion, // por ahora, "volver" = cerrar sesión
                            onContactar = { /* no aplica: es tu propio perfil */ }
                        )
                    } else {
                        PantallaPerfilCliente(
                            usuario = usuario,
                            numeroReparaciones = 0, // TODO: calcular desde la BD
                            miembroDesde = "—", // TODO: guardar fecha de registro
                            onVolver = onCerrarSesion,
                            onEditarPerfil = { /* TODO */ }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PantallaPlaceholder(texto: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(texto, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}