package com.example.teccerca.data.repositorio

import com.example.teccerca.data.modelo.UsuarioLogin
import com.example.teccerca.data.modelo.UsuarioRegistro
import com.example.teccerca.data.remoto.ApiServicio
import com.example.teccerca.data.respuesta.LoginRespuesta
import com.example.teccerca.data.respuesta.RegistroRespuesta


class UsuarioRepositorio {


    private val api = ApiServicio.usuarioApi



    suspend fun iniciarSesion(
        usuario: UsuarioLogin
    ): LoginRespuesta? {


        return try {


            println("REPOSITORIO: enviando petición login")


            val respuesta = api.iniciarSesion(usuario)



            println("REPOSITORIO: código HTTP ${respuesta.code()}")

            println("REPOSITORIO: mensaje ${respuesta.message()}")

            println("REPOSITORIO: body ${respuesta.body()}")



            if (respuesta.isSuccessful) {


                respuesta.body()


            } else {


                null


            }



        } catch (e: Exception) {


            println("ERROR REPOSITORIO LOGIN: ${e.message}")

            throw e


        }


    }



    suspend fun registrarUsuario(
        usuario: UsuarioRegistro
    ): RegistroRespuesta? {


        return try {


            println("REPOSITORIO: enviando registro")


            val respuesta = api.registrarUsuario(usuario)



            println("REPOSITORIO: código HTTP ${respuesta.code()}")

            println("REPOSITORIO: mensaje ${respuesta.message()}")

            println("REPOSITORIO: body ${respuesta.body()}")



            if (respuesta.isSuccessful) {


                respuesta.body()


            } else {


                null


            }



        } catch (e: Exception) {


            println("ERROR REPOSITORIO REGISTRO: ${e.message}")

            throw e


        }


    }


}