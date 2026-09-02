package com.example.teccerca.data.modelo

data class Usuario(
    val id: String = "",
    val nombre: String = "",
    val correo: String = "",
    val rol: String = "Cliente" // Cliente o Técnico
)