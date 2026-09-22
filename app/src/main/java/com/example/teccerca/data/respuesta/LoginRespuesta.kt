package com.example.teccerca.data.respuesta

data class LoginRespuesta(
    val mensaje:String,
    val usuario:UsuarioRespuesta
)


data class UsuarioRespuesta(
    val idUsuario:Int,
    val nombre:String,
    val correo:String,
    val idRol:Int
)