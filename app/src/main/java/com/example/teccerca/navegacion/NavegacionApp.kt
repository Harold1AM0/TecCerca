package com.example.teccerca.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teccerca.ui.autenticacion.LoginPantalla
import com.example.teccerca.ui.autenticacion.RegistroPantalla
import com.example.teccerca.ui.cliente.InicioClientePantalla
import com.example.teccerca.ui.tecnico.InicioTecnicoPantalla


@Composable
fun NavegacionApp() {


    val navController = rememberNavController()


    NavHost(

        navController = navController,

        startDestination = Rutas.LOGIN

    ) {


        composable(
            Rutas.LOGIN
        ) {


            LoginPantalla(

                irCliente = {

                    navController.navigate(
                        Rutas.CLIENTE
                    )

                },


                irTecnico = {

                    navController.navigate(
                        Rutas.TECNICO
                    )

                },


                irRegistro = {

                    navController.navigate(
                        Rutas.REGISTRO
                    )

                }

            )

        }



        composable(
            Rutas.CLIENTE
        ) {

            InicioClientePantalla()

        }



        composable(
            Rutas.TECNICO
        ) {

            InicioTecnicoPantalla()

        }



        composable(
            Rutas.REGISTRO
        ) {

            RegistroPantalla()

        }


    }


}