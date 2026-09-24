package com.example.teccerca.data.repositorio

import com.example.teccerca.data.modelo.SolicitudRequest
import com.example.teccerca.data.remoto.ApiServicio
import com.example.teccerca.data.respuesta.SolicitudRespuesta

class SolicitudRepositorio {

    private val api = ApiServicio.solicitudApi

    suspend fun crearSolicitud(
        solicitud: SolicitudRequest
    ): SolicitudRespuesta? {
        val respuesta = api.crearSolicitud(solicitud)

        return if (respuesta.isSuccessful) {
            respuesta.body()
        } else {
            null
        }
    }
}
