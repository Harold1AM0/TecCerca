package com.example.teccerca.data.repositorio

import com.example.teccerca.data.modelo.SolicitudServicio

class RepositorioSolicitud {
    fun crearSolicitud(solicitud: SolicitudServicio) { } // HU06[cite: 1]
    fun obtenerSolicitudesTecnico(idTecnico: String): List<SolicitudServicio> = emptyList() // HU07[cite: 1]
    fun actualizarEstadoSolicitud(idSolicitud: String, nuevoEstado: String) { } // HU07[cite: 1]
}