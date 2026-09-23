package com.example.teccerca.data.modelo


data class Tecnico(

    val idTecnico: Int,

    val experiencia: Int,

    val descripcion: String?,

    val latitud: String?,

    val longitud: String?,

    val puntajePromedio: String,

    val disponible: Boolean,

    val usuario: UsuarioTecnico

)



data class UsuarioTecnico(

    val nombre: String,

    val telefono: String

)