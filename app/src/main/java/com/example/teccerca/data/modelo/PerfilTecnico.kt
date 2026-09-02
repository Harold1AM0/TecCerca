package com.example.teccerca.data.modelo

data class PerfilTecnico( // HU02[cite: 1]
    val idTecnico: String = "",
    val especialidades: List<String> = emptyList(),
    val experiencia: String = "",
    val calificacionPromedio: Double = 0.0,
    val ubicacionLat: Double = 0.0,
    val ubicacionLng: Double = 0.0
)