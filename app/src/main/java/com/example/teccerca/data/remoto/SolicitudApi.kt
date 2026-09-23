package com.example.teccerca.data.remoto

import com.example.teccerca.data.modelo.SolicitudRequest
import com.example.teccerca.data.respuesta.SolicitudRespuesta
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface SolicitudApi {


    @POST("solicitudes")
    suspend fun crearSolicitud(

        @Body solicitud: SolicitudRequest

    ): Response<SolicitudRespuesta>


}