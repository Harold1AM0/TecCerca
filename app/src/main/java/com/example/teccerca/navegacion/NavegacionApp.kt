package com.example.teccerca.navegacion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teccerca.ui.autenticacion.PantallaLogin
import com.example.teccerca.ui.autenticacion.PantallaRegistro
import com.example.teccerca.ui.principal.PantallaPrincipal
import com.example.teccerca.ui.viewmodel.AuthViewModel
import com.example.teccerca.ui.viewmodel.EstadoAuth

@Composable
fun NavegacionApp() {
    val navController = rememberNavController()

    // Se crea una sola vez a nivel de NavegacionApp para que el estado
    // (usuario logueado) sobreviva al navegar entre pantallas.
    val authViewModel: AuthViewModel = viewModel()
    val estadoAuth by authViewModel.estado.collectAsState()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LaunchedEffect(estadoAuth) {
                if (estadoAuth is EstadoAuth.Exito) {
                    navController.navigate("principal") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }

            PantallaLogin(
                onIniciarSesion = { correo, contrasena ->
                    authViewModel.iniciarSesion(correo, contrasena)
                },
                onOlvideContrasena = {
                    // TODO: pantalla de recuperar contraseña (aún no existe)
                },
                onRegistrarse = {
                    authViewModel.resetearEstado()
                    navController.navigate("registro")
                }
            )
            // TODO: mostrar (estadoAuth as? EstadoAuth.Error)?.mensaje en PantallaLogin
        }

        composable("registro") {
            LaunchedEffect(estadoAuth) {
                if (estadoAuth is EstadoAuth.Exito) {
                    // Registro exitoso: NO logueamos automático, regresamos al login
                    authViewModel.resetearEstado()
                    navController.navigate("login") {
                        popUpTo("registro") { inclusive = true }
                    }
                }
            }

            val mensajeError = (estadoAuth as? EstadoAuth.Error)?.mensaje

            PantallaRegistro(
                onRegistrar = { rol, nombre, apellidos, correo, contrasena, edad, especialidad ->
                    if (rol == "Tecnico") {
                        authViewModel.registrarTecnico(nombre, apellidos, correo, contrasena, edad, especialidad ?: "")
                    } else {
                        authViewModel.registrarCliente(nombre, apellidos, correo, contrasena, edad)
                    }
                },
                onVolver = {
                    authViewModel.resetearEstado()
                    navController.popBackStack()
                },
                mensajeError = mensajeError
            )
        }

        // TODO: descomentar cuando exista la función @Composable en cada archivo
        // composable("buscar_categoria") { PantallaBuscarCategoria() }
        // composable("tecnicos_cercanos") { PantallaTecnicosCercanos() }
        // composable("crear_solicitud") { PantallaCrearSolicitud() }
        // composable("calificar_servicio") { PantallaCalificarServicio() }
        // composable("gestionar_solicitudes") { PantallaGestionarSolicitudes() }

        composable("principal") {
            val usuario = (estadoAuth as? EstadoAuth.Exito)?.usuario
            if (usuario != null) {
                PantallaPrincipal(
                    usuario = usuario,
                    onCerrarSesion = {
                        authViewModel.resetearEstado()
                        navController.navigate("login") {
                            popUpTo("principal") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}