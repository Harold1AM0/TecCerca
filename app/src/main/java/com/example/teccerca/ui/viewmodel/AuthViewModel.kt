package com.example.teccerca.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.teccerca.data.modelo.Usuario
import com.example.teccerca.data.repositorio.RepositorioAutenticacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Tipo de archivo: Kotlin file con una sealed class + una clase (AndroidViewModel)

sealed class EstadoAuth {
    object Inactivo : EstadoAuth()
    object Cargando : EstadoAuth()
    data class Exito(val usuario: Usuario) : EstadoAuth()
    data class Error(val mensaje: String) : EstadoAuth()
}

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repositorio = RepositorioAutenticacion(application.applicationContext)

    private val _estado = MutableStateFlow<EstadoAuth>(EstadoAuth.Inactivo)
    val estado: StateFlow<EstadoAuth> = _estado

    fun iniciarSesion(correo: String, contrasena: String) {
        viewModelScope.launch {
            _estado.value = EstadoAuth.Cargando
            val usuario = repositorio.iniciarSesion(correo, contrasena)
            _estado.value = if (usuario != null) {
                EstadoAuth.Exito(usuario)
            } else {
                EstadoAuth.Error("Correo o contraseña incorrectos")
            }
        }
    }

    fun registrarCliente(nombre: String, apellidos: String, correo: String, contrasena: String, edad: String) {
        viewModelScope.launch {
            _estado.value = EstadoAuth.Cargando
            val resultado = repositorio.registrarCliente(nombre, apellidos, correo, contrasena, edad)
            _estado.value = resultado.fold(
                onSuccess = {
                    EstadoAuth.Exito(
                        Usuario(nombre = nombre, apellidos = apellidos, correo = correo, contrasena = contrasena, edad = edad, rol = "Cliente")
                    )
                },
                onFailure = { EstadoAuth.Error(it.message ?: "No se pudo registrar") }
            )
        }
    }

    fun registrarTecnico(nombre: String, apellidos: String, correo: String, contrasena: String, edad: String, especialidad: String) {
        viewModelScope.launch {
            _estado.value = EstadoAuth.Cargando
            val resultado = repositorio.registrarTecnico(nombre, apellidos, correo, contrasena, edad, especialidad)
            _estado.value = resultado.fold(
                onSuccess = {
                    EstadoAuth.Exito(
                        Usuario(nombre = nombre, apellidos = apellidos, correo = correo, contrasena = contrasena, edad = edad, rol = "Tecnico", especialidad = especialidad)
                    )
                },
                onFailure = { EstadoAuth.Error(it.message ?: "No se pudo registrar") }
            )
        }
    }

    fun resetearEstado() {
        _estado.value = EstadoAuth.Inactivo
    }
}