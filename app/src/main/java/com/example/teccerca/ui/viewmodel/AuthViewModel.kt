package com.example.teccerca.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.teccerca.data.repositorio.RepositorioAutenticacion
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repositorio = RepositorioAutenticacion(application.applicationContext)

    fun iniciarSesion(correo: String, contrasena: String) {
        viewModelScope.launch {
            val usuario = repositorio.iniciarSesion(correo, contrasena)
            // TODO: exponer el resultado (por ejemplo con un StateFlow) para
            // que la pantalla sepa si el login fue exitoso o no y navegue.
        }
    }
}