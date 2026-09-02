package com.example.teccerca.ubicacion

import android.content.Context

class AsistenteUbicacion(private val context: Context) {
    fun solicitarPermisosUbicacion() { } // HU04[cite: 1]
    fun obtenerUbicacionActual(): Pair<Double, Double> {
        return Pair(0.0, 0.0)
    }
}