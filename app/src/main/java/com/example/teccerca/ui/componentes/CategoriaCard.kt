package com.example.teccerca.ui.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun CategoriaCard(nombre: String, seleccionada: Boolean, cantidad: Int? = null, onClick: () -> Unit) {
    val icono = when {
        nombre.contains("comput", true) || nombre.contains("laptop", true) -> Icons.Outlined.Computer
        nombre.contains("electri", true) -> Icons.Outlined.Bolt
        nombre.contains("celular", true) -> Icons.Outlined.PhoneAndroid
        nombre.contains("plom", true) -> Icons.Outlined.WaterDrop
        else -> Icons.Outlined.Build
    }
    val acento = when {
        nombre.contains("electri", true) -> Color(0xFF9C7400)
        nombre.contains("plom", true) -> Color(0xFF0089B8)
        else -> MaterialTheme.colorScheme.primary
    }
    val titulo = nombre.removePrefix("Reparación de ").removePrefix("Reparacion de ")
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().heightIn(min = 132.dp).semantics { selected = seleccionada },
        border = BorderStroke(1.dp, if (seleccionada) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Surface(shape = RoundedCornerShape(10.dp), color = acento) {
                    Icon(icono, null, Modifier.padding(8.dp).size(22.dp), tint = Color.White)
                }
                Spacer(Modifier.weight(1f))
                if (cantidad != null) Text("$cantidad", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(titulo, style = MaterialTheme.typography.titleSmall)
                Text(if (cantidad == null) "Consultar técnicos" else if (cantidad == 1) "Técnico cercano" else "Técnicos cercanos",
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
