package com.example.teccerca.data.modelo

data class UsuarioRegistro(
    val nombre: String,
    val correo: String,
    val password: String,
    val telefono: String?,
    val idRol: Int,
    val experiencia: Int? = null,
    val descripcion: String? = null,
    val especialidades: List<Int>? = null
)