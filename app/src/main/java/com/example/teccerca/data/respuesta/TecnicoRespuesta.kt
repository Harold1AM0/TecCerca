package com.example.teccerca.data.respuesta


data class TecnicoRespuesta(

    val idTecnico: Int,

    val nombre: String,

    val telefono: String?,

    val experiencia: Int?,

    val descripcion: String?,

    val puntajePromedio: String?,

    val distanciaKm: Double

)