package com.example.teccerca.data.remoto

import com.example.teccerca.data.modelo.UsuarioLogin
import com.example.teccerca.data.modelo.UsuarioRegistro
import com.example.teccerca.data.respuesta.LoginRespuesta
import com.example.teccerca.data.respuesta.RegistroRespuesta
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface UsuarioApi {


    @Headers(
        "Content-Type: application/json"
    )
    @POST("auth/login")
    suspend fun iniciarSesion(

        @Body usuario: UsuarioLogin

    ): Response<LoginRespuesta>



    @Headers(
        "Content-Type: application/json"
    )
    @POST("auth/registro")
    suspend fun registrarUsuario(

        @Body usuario: UsuarioRegistro

    ): Response<RegistroRespuesta>


}