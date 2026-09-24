package com.example.teccerca.data.repositorio

import com.example.teccerca.data.remoto.ConfiguracionApi
import com.example.teccerca.data.remoto.EspecialidadApi

class EspecialidadRepositorio {
    private val api = ConfiguracionApi.retrofit.create(EspecialidadApi::class.java)
    suspend fun obtenerEspecialidades() = api.obtenerEspecialidades()
}
