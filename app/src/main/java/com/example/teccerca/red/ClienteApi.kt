package com.example.teccerca.red

import com.example.teccerca.data.modelo.PerfilTecnico
import retrofit2.http.GET
import retrofit2.http.Query

interface ClienteApi {
    @GET("tecnicos/cercanos")
    suspend fun obtenerTecnicosCercanos(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double
    ): List<PerfilTecnico>
}