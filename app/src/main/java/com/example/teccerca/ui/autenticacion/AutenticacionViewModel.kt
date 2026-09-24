package com.example.teccerca.ui.autenticacion

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teccerca.data.modelo.UsuarioLogin
import com.example.teccerca.data.repositorio.UsuarioRepositorio
import com.example.teccerca.data.respuesta.UsuarioRespuesta
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class AutenticacionViewModel : ViewModel() {
    private val repositorio = UsuarioRepositorio()
    private val _mensaje = mutableStateOf("")
    val mensaje: State<String> = _mensaje
    private val _cargando = mutableStateOf(false)
    val cargando: State<Boolean> = _cargando
    private val _usuario = mutableStateOf<UsuarioRespuesta?>(null)
    val usuario: State<UsuarioRespuesta?> = _usuario

    fun iniciarSesion(correo: String, password: String, onResultado: (Int?) -> Unit) {
        if (_cargando.value) return
        _cargando.value = true
        _mensaje.value = ""
        _usuario.value = null
        viewModelScope.launch {
            try {
                val respuesta = repositorio.iniciarSesion(UsuarioLogin(correo.trim(), password))
                if (respuesta != null) {
                    _usuario.value = respuesta.usuario
                    onResultado(respuesta.usuario.idRol)
                } else {
                    _mensaje.value = "No pudimos iniciar sesión. Revisa tu correo y contraseña."
                    onResultado(null)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _mensaje.value = "Error de conexión, intenta de nuevo"
                onResultado(null)
            } finally {
                _cargando.value = false
            }
        }
    }
}
