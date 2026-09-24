package com.example.teccerca

import com.example.teccerca.data.respuesta.TecnicoRespuesta
import com.example.teccerca.ui.cliente.filtrarTecnicos
import org.junit.Assert.*
import org.junit.Test

class FiltroTecnicosTest {
    private val carlos = TecnicoRespuesta(2, "Carlos Ramírez", null, 5, "Reparación de laptops", "4.8", 0.03, listOf("Computadoras"))
    private val ana = TecnicoRespuesta(3, "Ana Torres", null, null, "Instalaciones eléctricas", null, 0.4, listOf("Electricidad"))

    @Test fun buscaSinTildesPorNombreDescripcionYEspecialidad() {
        val lista = listOf(carlos, ana)
        assertEquals(listOf(carlos), filtrarTecnicos(lista, "  RAMIREZ laptop ", null))
        assertEquals(listOf(ana), filtrarTecnicos(lista, "electricidad", null))
        assertEquals(lista, filtrarTecnicos(lista, "", null))
        assertEquals(listOf(ana), filtrarTecnicos(lista, "electricista", null))
    }

    @Test fun combinaCategoriaYConsultaSinInventarResultados() {
        assertEquals(emptyList<TecnicoRespuesta>(), filtrarTecnicos(listOf(carlos, ana), "Carlos", "Electricidad"))
        assertEquals(listOf(ana), filtrarTecnicos(listOf(carlos, ana), "", "Electricidad"))
        assertEquals(emptyList<TecnicoRespuesta>(), filtrarTecnicos(listOf(carlos), "plomería", null))
    }
}
