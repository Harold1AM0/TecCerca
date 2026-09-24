package com.example.teccerca.ui.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BotonPrincipal(
    texto: String,
    onClick: () -> Unit,
    habilitado: Boolean = true,
    cargando: Boolean = false
) {
    Button(
        onClick = onClick,
        enabled = habilitado && !cargando,
        modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        if (cargando) {
            CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
            Spacer(Modifier.width(12.dp))
            Text("Enviando…")
        } else Text(texto)
    }
}
