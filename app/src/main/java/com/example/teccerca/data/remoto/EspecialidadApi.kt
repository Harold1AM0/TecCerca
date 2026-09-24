package com.example.teccerca.data.remoto

import com.example.teccerca.data.modelo.Especialidad
import retrofit2.http.GET

interface EspecialidadApi {
    @GET("especialidades")
    suspend fun obtenerEspecialidades(): List<Especialidad>
}
