package com.example.teccerca.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teccerca.ui.autenticacion.*
import com.example.teccerca.ui.cliente.*
import com.example.teccerca.ui.tecnico.*

@Composable
fun NavegacionApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { PantallaLogin() }
        composable("registro_cliente") { PantallaRegistroCliente() }
        composable("registro_tecnico") { PantallaRegistroTecnico() }
        composable("buscar_categoria") { PantallaBuscarCategoria() }
        composable("tecnicos_cercanos") { PantallaTecnicosCercanos() }
        composable("perfil_tecnico") { PantallaPerfilTecnico() }
        composable("crear_solicitud") { PantallaCrearSolicitud() }
        composable("calificar_servicio") { PantallaCalificarServicio() }
        composable("gestionar_solicitudes") { PantallaGestionarSolicitudes() }
    }
}