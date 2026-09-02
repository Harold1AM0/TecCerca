package com.example.teccerca.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.teccerca.data.repositorio.RepositorioAutenticacion

class AuthViewModel(private val repositorio: RepositorioAutenticacion = RepositorioAutenticacion()) : ViewModel() {
    fun iniciarSesion(correo: String, contrasena: String) {
        repositorio.iniciarSesion(correo, contrasena)
    }
}