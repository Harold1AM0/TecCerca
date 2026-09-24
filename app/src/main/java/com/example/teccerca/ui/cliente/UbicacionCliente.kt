package com.example.teccerca.ui.cliente

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

@Composable
fun UbicacionCliente(viewModel: ClienteViewModel) {
    val contexto = LocalContext.current
    var buscando by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }
    val cancelacion = remember { CancellationTokenSource() }
    DisposableEffect(Unit) { onDispose { cancelacion.cancel() } }

    fun localizar() {
        buscando = true
        error = ""
        try {
            LocationServices.getFusedLocationProviderClient(contexto)
                .getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, cancelacion.token)
                .addOnSuccessListener { ubicacion ->
                    buscando = false
                    if (ubicacion == null) {
                        error = "No pudimos obtener tu ubicación. Activa la ubicación del dispositivo e inténtalo otra vez."
                    } else {
                        viewModel.usarUbicacion(ubicacion.latitude, ubicacion.longitude)
                    }
                }
                .addOnFailureListener {
                    buscando = false
                    error = "No pudimos obtener tu ubicación. Puedes seguir buscando en Lima centro."
                }
        } catch (e: SecurityException) {
            buscando = false
            error = "Autoriza la ubicación para buscar cerca de ti."
        }
    }

    val permiso = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permisos ->
        if (permisos.values.any { it }) localizar()
        else error = "Puedes continuar con la zona de búsqueda actual."
    }
    Column {
        Text("Zona: ${viewModel.zona.value} · Radio de 5 km", style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        TextButton(
            enabled = !buscando && !viewModel.cargando.value,
            onClick = {
                if (ContextCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    localizar()
                } else permiso.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))
            }
        ) {
            Icon(Icons.Default.MyLocation, null)
            Text(if (buscando) " Obteniendo ubicación…" else " Usar mi ubicación")
        }
        if (error.isNotEmpty()) Text(error, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
    }
}
