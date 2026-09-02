package com.example.teccerca.data.repositorio

import android.content.Context
import com.example.teccerca.data.local.AppDatabase
import com.example.teccerca.data.modelo.Usuario

class RepositorioAutenticacion(context: Context) {

    private val usuarioDao = AppDatabase.getDatabase(context).usuarioDao()

    suspend fun iniciarSesion(correo: String, contrasena: String): Usuario? {
        return usuarioDao.iniciarSesion(correo, contrasena)
    }

    suspend fun registrarCliente(
        nombre: String,
        apellidos: String,
        correo: String,
        contrasena: String,
        edad: String
    ): Result<Long> {
        if (usuarioDao.existeCorreo(correo) > 0) {
            return Result.failure(Exception("Ese correo ya está registrado"))
        }
        val usuario = Usuario(
            nombre = nombre,
            apellidos = apellidos,
            correo = correo,
            contrasena = contrasena,
            edad = edad,
            rol = "Cliente"
        )
        return Result.success(usuarioDao.insertar(usuario))
    }

    suspend fun registrarTecnico(
        nombre: String,
        apellidos: String,
        correo: String,
        contrasena: String,
        edad: String,
        especialidad: String
    ): Result<Long> {
        if (usuarioDao.existeCorreo(correo) > 0) {
            return Result.failure(Exception("Ese correo ya está registrado"))
        }
        val usuario = Usuario(
            nombre = nombre,
            apellidos = apellidos,
            correo = correo,
            contrasena = contrasena,
            edad = edad,
            rol = "Tecnico",
            especialidad = especialidad
        )
        return Result.success(usuarioDao.insertar(usuario))
    }
}