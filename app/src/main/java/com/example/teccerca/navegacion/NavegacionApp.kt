package com.example.teccerca.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teccerca.data.modelo.PerfilTecnico
import com.example.teccerca.data.modelo.Usuario
import com.example.teccerca.ui.autenticacion.*
import com.example.teccerca.ui.cliente.*
import com.example.teccerca.ui.tecnico.*

@Composable
fun NavegacionApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            PantallaLogin(
                onIniciarSesion = { _, _ ->
                    // TODO: validar con RepositorioAutenticacion/AuthViewModel antes de navegar
                    navController.navigate("perfil_cliente")
                },
                onOlvideContrasena = {
                    // TODO: pantalla de recuperar contraseña (aún no existe)
                },
                onRegistrarse = {
                    navController.navigate("registro_cliente")
                }
            )
        }

        // TODO: descomentar cuando exista la función @Composable en cada archivo
        // composable("registro_cliente") { PantallaRegistroCliente() }
        // composable("registro_tecnico") { PantallaRegistroTecnico() }
        // composable("buscar_categoria") { PantallaBuscarCategoria() }
        // composable("tecnicos_cercanos") { PantallaTecnicosCercanos() }
        // composable("crear_solicitud") { PantallaCrearSolicitud() }
        // composable("calificar_servicio") { PantallaCalificarServicio() }
        // composable("gestionar_solicitudes") { PantallaGestionarSolicitudes() }

        composable("perfil_cliente") {
            PantallaPerfilCliente(
                usuario = Usuario( // TODO: reemplazar por el usuario real logueado
                    nombre = "María",
                    apellidos = "González",
                    correo = "maria.gonzalez@email.com",
                    contrasena = "",
                    edad = "28",
                    rol = "Cliente"
                ),
                numeroReparaciones = 12,
                miembroDesde = "Ene 2024",
                onVolver = { navController.popBackStack() },
                onEditarPerfil = { /* TODO */ }
            )
        }

        composable("perfil_tecnico") {
            PantallaPerfilTecnico(
                perfil = PerfilTecnico( // TODO: reemplazar por el técnico real
                    nombre = "Carlos",
                    apellido = "Ramírez",
                    edad = 35,
                    correoElectronico = "carlos.ramirez@email.com",
                    especialidad = "Reparación de Computadoras",
                    calificacionPromedio = 4.5,
                    numeroOpiniones = 124,
                    estaActivo = true,
                    esEspecialistaVerificado = true
                ),
                onVolver = { navController.popBackStack() },
                onContactar = { /* TODO */ }
            )
        }
    }
}