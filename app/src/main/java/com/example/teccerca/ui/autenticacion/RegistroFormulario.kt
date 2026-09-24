package com.example.teccerca.ui.autenticacion

import com.example.teccerca.data.modelo.UsuarioRegistro

data class RegistroFormulario(
    val nombre: String = "",
    val correo: String = "",
    val telefono: String = "",
    val password: String = "",
    val confirmacion: String = "",
    val idRol: Int = 1,
    val experiencia: String = "",
    val descripcion: String = "",
    val especialidades: List<Int> = emptyList()
) {
    fun solicitud() = UsuarioRegistro(
        nombre = nombre.trim(),
        correo = correo.trim(),
        password = password,
        telefono = telefono.trim().takeIf { it.isNotEmpty() },
        idRol = idRol,
        experiencia = if (idRol == 2) experiencia.trim().toIntOrNull() else null,
        descripcion = if (idRol == 2) descripcion.trim() else null,
        especialidades = if (idRol == 2) especialidades else null
    )
}

fun validarRegistro(formulario: RegistroFormulario): Map<String, String> = buildMap {
    if (formulario.nombre.trim().length < 2) put("nombre", "Ingresa tu nombre completo.")
    if (!Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$").matches(formulario.correo.trim())) {
        put("correo", "Ingresa un correo válido.")
    }
    if (formulario.telefono.isNotBlank() &&
        (!Regex("^\\+?[0-9 ()-]+$").matches(formulario.telefono.trim()) ||
            formulario.telefono.count { it.isDigit() } !in 7..15)) {
        put("telefono", "Ingresa un teléfono válido o deja este campo vacío.")
    }
    if (formulario.password.length < 8) put("password", "Usa al menos 8 caracteres.")
    // bcrypt, usado por el backend, admite como máximo 72 bytes por contraseña.
    if (formulario.password.toByteArray(Charsets.UTF_8).size > 72) put("password", "La contraseña es demasiado larga.")
    if (formulario.confirmacion != formulario.password || formulario.confirmacion.isEmpty()) {
        put("confirmacion", "Las contraseñas deben coincidir.")
    }
    if (formulario.idRol !in 1..2) put("rol", "Selecciona un tipo de cuenta válido.")
    if (formulario.idRol == 2) {
        val experiencia = formulario.experiencia.trim()
        if (experiencia.isNotEmpty() && (experiencia.toIntOrNull() == null || experiencia.toInt() < 0)) {
            put("experiencia", "Ingresa los años de experiencia como un número entero positivo o cero.")
        }
        if (formulario.descripcion.isBlank()) put("descripcion", "Cuéntanos qué servicios ofreces.")
        if (formulario.especialidades.isEmpty()) put("especialidades", "Selecciona al menos una especialidad.")
    }
}
