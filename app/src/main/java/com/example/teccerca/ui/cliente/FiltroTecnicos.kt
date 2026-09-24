package com.example.teccerca.ui.cliente

import com.example.teccerca.data.respuesta.TecnicoRespuesta
import java.text.Normalizer
import java.util.Locale

internal fun normalizarBusqueda(texto: String): String =
    Normalizer.normalize(texto, Normalizer.Form.NFD)
        .replace(Regex("\\p{M}+"), "")
        .lowercase(Locale.ROOT)

fun filtrarTecnicos(
    tecnicos: List<TecnicoRespuesta>,
    consulta: String,
    especialidad: String?
): List<TecnicoRespuesta> {
    val palabras = normalizarBusqueda(consulta.trim()).split(Regex("\\s+")).filter { it.isNotBlank() }
    return tecnicos.filter { tecnico ->
        val servicios = normalizarBusqueda(tecnico.especialidades.joinToString(" "))
        val palabrasServicio = buildString {
            if ("electric" in servicios) append(" electricista eléctrico electricidad")
            if ("comput" in servicios) append(" laptop computadora ordenador pc")
            if ("celular" in servicios || "movil" in servicios) append(" celular teléfono móvil smartphone")
        }
        val contenido = normalizarBusqueda(
            listOf(tecnico.nombre, tecnico.descripcion.orEmpty(), servicios, palabrasServicio).joinToString(" ")
        )
        (especialidad == null || especialidad in tecnico.especialidades) &&
            palabras.all { it in contenido }
    }
}
