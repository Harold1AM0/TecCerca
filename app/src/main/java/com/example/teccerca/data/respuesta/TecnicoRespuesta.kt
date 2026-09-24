package com.example.teccerca.data.respuesta

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TecnicoRespuesta(
    val idTecnico: Int,
    val nombre: String,
    val telefono: String?,
    val experiencia: Int?,
    val descripcion: String?,
    val puntajePromedio: String?,
    val distanciaKm: Double,
    val especialidades: List<String> = emptyList()
) : Parcelable