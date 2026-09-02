package com.example.teccerca.data.modelo

data class SolicitudServicio( // HU06, HU07[cite: 1]
    val idSolicitud: String = "",
    val idCliente: String = "",
    val idTecnico: String = "",
    val descripcionProblema: String = "",
    val ubicacionServicio: String = "",
    val estado: String = "Pendiente"
)