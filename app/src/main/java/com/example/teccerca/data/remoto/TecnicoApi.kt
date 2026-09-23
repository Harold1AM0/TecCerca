package com.example.teccerca.data.remoto

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.teccerca.data.modelo.UbicacionRequest
import com.example.teccerca.data.respuesta.TecnicoRespuesta


interface TecnicoApi {


    @POST("tecnicos/cercanos")
    suspend fun obtenerTecnicosCercanos(
        @Body ubicacion: UbicacionRequest
    ): Response<List<TecnicoRespuesta>>


}