package com.example.teccerca.ui.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BarraInferior(seleccionada: String, onBuscar: () -> Unit, onCercanos: () -> Unit, onPerfil: () -> Unit) {
    Column {
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        NavigationBar(containerColor = MaterialTheme.colorScheme.surface, tonalElevation = 0.dp) {
            listOf(
                Triple("Buscar", Icons.Outlined.Search, onBuscar),
                Triple("Cercanos", Icons.Outlined.LocationOn, onCercanos),
                Triple("Perfil", Icons.Outlined.PersonOutline, onPerfil)
            ).forEach { (titulo, icono, accion) ->
                NavigationBarItem(
                    selected = seleccionada == titulo, onClick = accion,
                    icon = { Icon(icono, contentDescription = null) },
                    label = { Text(titulo) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.surface,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}
