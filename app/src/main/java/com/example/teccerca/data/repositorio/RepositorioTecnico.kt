package com.example.teccerca.data.repositorio

import com.example.teccerca.data.modelo.PerfilTecnico

class RepositorioTecnico {
    fun buscarPorCategoria(categoria: String): List<PerfilTecnico> = emptyList() // HU03[cite: 1]
    fun obtenerTecnicosCercanos(lat: Double, lng: Double): List<PerfilTecnico> = emptyList() // HU04[cite: 1]
}