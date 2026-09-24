package com.example.teccerca.ui.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun BarraBusqueda(texto: String, onCambio: (String) -> Unit, onBuscar: () -> Unit = {}) {
    OutlinedTextField(
        value = texto, onValueChange = onCambio, modifier = Modifier.fillMaxWidth(),
        singleLine = true, shape = RoundedCornerShape(12.dp),
        placeholder = { Text("Buscar técnico o servicio…", style = MaterialTheme.typography.bodyMedium) },
        leadingIcon = { Icon(Icons.Outlined.Search, null) },
        trailingIcon = {
            if (texto.isNotEmpty()) IconButton(onClick = { onCambio("") }) {
                Icon(Icons.Outlined.Close, "Limpiar búsqueda")
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onBuscar() })
    )
}
