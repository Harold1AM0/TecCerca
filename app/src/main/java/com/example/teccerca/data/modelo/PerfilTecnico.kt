package com.example.teccerca.data.modelo

/**
 * Modelo de datos que representa el perfil de un técnico.
 * Tipo de archivo: Kotlin data class
 */
data class PerfilTecnico(
    val id: String = "",
    val nombre: String = "",
    val apellido: String = "",
    val edad: Int = 0,
    val correoElectronico: String = "",
    val especialidad: String = "",
    val calificacionPromedio: Double = 0.0,
    val numeroOpiniones: Int = 0,
    val estaActivo: Boolean = false,
    val esEspecialistaVerificado: Boolean = false,
    val fotoUrl: String? = null
)