package com.example.teccerca.navegacion

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.*
import com.example.teccerca.data.local.ConfiguracionDemo
import com.example.teccerca.data.respuesta.TecnicoRespuesta
import com.example.teccerca.ui.autenticacion.*
import com.example.teccerca.ui.cliente.*
import com.example.teccerca.ui.componentes.EstadoCliente
import com.example.teccerca.ui.tecnico.InicioTecnicoPantalla
import com.example.teccerca.ui.perfil.PerfilClientePantalla

@Composable
fun NavegacionApp() {
    val navController = rememberNavController()
    var nombre by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var idUsuario by rememberSaveable { mutableStateOf(0) }
    var dialogo by remember { mutableStateOf<String?>(null) }

    fun inicio() {
        navController.popBackStack(Rutas.CLIENTE, false)
    }

    fun abrirTecnico(tecnico: TecnicoRespuesta) {
        navController.getBackStackEntry(Rutas.CLIENTE).savedStateHandle["tecnico"] = tecnico
        navController.navigate(Rutas.DETALLE_TECNICO) { launchSingleTop = true }
    }

    @Composable
    fun clienteViewModel(entry: NavBackStackEntry): ClienteViewModel {
        val padre = remember(entry) { navController.getBackStackEntry(Rutas.CLIENTE) }
        return viewModel(viewModelStoreOwner = padre)
    }

    fun cercanos() {
        navController.popBackStack(Rutas.CLIENTE, false)
        navController.navigate(Rutas.BUSCAR) { launchSingleTop = true }
    }

    fun perfil() {
        navController.navigate(Rutas.PERFIL) { launchSingleTop = true }
    }

    if (dialogo != null) {
        AlertDialog(
            onDismissRequest = { dialogo = null },
            title = { Text("Notificaciones") },
            text = { Text("Las notificaciones estarán disponibles próximamente. Al enviar un servicio, verás la confirmación en la app.") },
            confirmButton = { TextButton(onClick = { dialogo = null }) { Text("Entendido") } }
        )
    }
    NavHost(navController = navController, startDestination = Rutas.LOGIN) {
        composable(Rutas.LOGIN) { entry ->
            val correoRegistrado by entry.savedStateHandle.getStateFlow("correoRegistrado", "").collectAsState()
            LoginPantalla(
                irCliente = { usuario ->
                    nombre = usuario.nombre
                    correo = usuario.correo
                    idUsuario = usuario.idUsuario
                    navController.navigate(Rutas.CLIENTE) { popUpTo(Rutas.LOGIN) { inclusive = true } }
                },
                irTecnico = {
                    navController.navigate(Rutas.TECNICO) { popUpTo(Rutas.LOGIN) { inclusive = true } }
                },
                irRegistro = { navController.navigate(Rutas.REGISTRO) },
                correoInicial = correoRegistrado
            )
        }
        composable(Rutas.CLIENTE) { entry ->
            InicioClientePantalla(
                nombre, clienteViewModel(entry),
                onBuscar = { navController.navigate(Rutas.BUSCAR) { launchSingleTop = true } },
                onTecnico = ::abrirTecnico,
                onPerfil = ::perfil,
                onNotificaciones = { dialogo = "notificaciones" }
            )
        }
        composable(Rutas.BUSCAR) { entry ->
            TecnicosCercanosPantalla(clienteViewModel(entry), ::inicio, ::abrirTecnico, ::perfil)
        }
        composable(Rutas.PERFIL) {
            PerfilClientePantalla(nombre, correo,
                onVolver = { navController.popBackStack() },
                onBuscar = ::inicio,
                onCercanos = ::cercanos,
                onCerrarSesion = {
                    nombre = ""
                    correo = ""
                    idUsuario = 0
                    navController.navigate(Rutas.LOGIN) { popUpTo(Rutas.CLIENTE) { inclusive = true } }
                }
            )
        }
        composable(Rutas.DETALLE_TECNICO) {
            val tecnico = navController.getBackStackEntry(Rutas.CLIENTE).savedStateHandle.get<TecnicoRespuesta>("tecnico")
            if (tecnico != null) {
                DetalleTecnicoPantalla(tecnico, { navController.popBackStack() }) {
                    navController.navigate("${Rutas.SOLICITUD}/${tecnico.idTecnico}") { launchSingleTop = true }
                }
            } else {
                Box(Modifier.fillMaxSize().safeDrawingPadding().padding(20.dp)) {
                    EstadoCliente("Selecciona un técnico", "Vuelve a la lista para ver su perfil.", "Volver al inicio", ::inicio)
                }
            }
        }
        composable(
            "${Rutas.SOLICITUD}/{idTecnico}",
            arguments = listOf(androidx.navigation.navArgument("idTecnico") { type = androidx.navigation.NavType.IntType })
        ) { entry ->
            val tecnico = navController.getBackStackEntry(Rutas.CLIENTE).savedStateHandle.get<TecnicoRespuesta>("tecnico")
            val tecnicoId = entry.arguments?.getInt("idTecnico") ?: 0
            if (tecnico != null && tecnico.idTecnico == tecnicoId) {
                SolicitudPantalla(tecnicoId, tecnico.nombre, ConfiguracionDemo.clientePara(idUsuario),
                    onVolver = { navController.popBackStack() },
                    onConfirmada = { solicitudId ->
                        navController.navigate("${Rutas.CONFIRMACION}/$solicitudId") {
                            popUpTo("${Rutas.SOLICITUD}/{idTecnico}") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            } else {
                Box(Modifier.fillMaxSize().safeDrawingPadding().padding(20.dp)) {
                    EstadoCliente("Selecciona un técnico", "Vuelve a la lista para solicitar el servicio.", "Volver al inicio", ::inicio)
                }
            }
        }
        composable(
            "${Rutas.CONFIRMACION}/{idSolicitud}",
            arguments = listOf(androidx.navigation.navArgument("idSolicitud") { type = androidx.navigation.NavType.IntType })
        ) { entry ->
            val tecnico = navController.getBackStackEntry(Rutas.CLIENTE).savedStateHandle.get<TecnicoRespuesta>("tecnico")
            ConfirmacionSolicitudPantalla(tecnico?.nombre ?: "El técnico", entry.arguments?.getInt("idSolicitud") ?: 0, ::inicio)
        }
        composable(Rutas.TECNICO) { InicioTecnicoPantalla() }
        composable(Rutas.REGISTRO) {
            RegistroPantalla(
                onVolver = { navController.popBackStack() },
                onIrLogin = { correoCreado ->
                    navController.getBackStackEntry(Rutas.LOGIN).savedStateHandle["correoRegistrado"] = correoCreado
                    navController.popBackStack(Rutas.LOGIN, false)
                }
            )
        }
    }
}
