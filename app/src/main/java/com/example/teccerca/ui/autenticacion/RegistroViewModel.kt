package com.example.teccerca.ui.autenticacion

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teccerca.data.modelo.Especialidad
import com.example.teccerca.data.modelo.UsuarioRegistro
import com.example.teccerca.data.repositorio.EspecialidadRepositorio
import com.example.teccerca.data.repositorio.UsuarioRepositorio
import com.example.teccerca.data.respuesta.RegistroRespuesta
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class RegistroViewModel @JvmOverloads constructor(
    private val enviar: suspend (UsuarioRegistro) -> RegistroRespuesta? = UsuarioRepositorio()::registrarUsuario,
    private val obtenerEspecialidades: suspend () -> List<Especialidad> = EspecialidadRepositorio()::obtenerEspecialidades
) : ViewModel() {
    // Las contraseñas permanecen solo en memoria y no se guardan en SavedStateHandle.
    private val _formulario = mutableStateOf(RegistroFormulario())
    val formulario: State<RegistroFormulario> = _formulario
    private val _errores = mutableStateOf<Map<String, String>>(emptyMap())
    val errores: State<Map<String, String>> = _errores
    private val _mensaje = mutableStateOf("")
    val mensaje: State<String> = _mensaje
    private val _cargando = mutableStateOf(false)
    val cargando: State<Boolean> = _cargando
    private val _registrado = mutableStateOf(false)
    val registrado: State<Boolean> = _registrado

    private val _especialidades = mutableStateOf<List<Especialidad>>(emptyList())
    val especialidades: State<List<Especialidad>> = _especialidades
    private val _cargandoEspecialidades = mutableStateOf(false)
    val cargandoEspecialidades: State<Boolean> = _cargandoEspecialidades
    private val _errorEspecialidades = mutableStateOf("")
    val errorEspecialidades: State<String> = _errorEspecialidades

    fun actualizar(formulario: RegistroFormulario) {
        if (_cargando.value || _registrado.value) return
        _formulario.value = formulario
        _mensaje.value = ""
        if (_errores.value.isNotEmpty()) _errores.value = validarRegistro(formulario)
        if (formulario.idRol == 2 && _especialidades.value.isEmpty()) cargarEspecialidades()
    }

    fun cargarEspecialidades() {
        if (_cargandoEspecialidades.value) return
        _cargandoEspecialidades.value = true
        _errorEspecialidades.value = ""
        viewModelScope.launch {
            try {
                _especialidades.value = obtenerEspecialidades()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _errorEspecialidades.value = "No pudimos cargar las especialidades."
            } finally {
                _cargandoEspecialidades.value = false
            }
        }
    }

    fun registrar() {
        if (_cargando.value || _registrado.value) return
        val formulario = _formulario.value
        _errores.value = validarRegistro(formulario)
        if (_errores.value.isNotEmpty()) {
            _mensaje.value = "Revisa los campos marcados antes de continuar."
            return
        }
        if (formulario.idRol == 2 &&
            (_cargandoEspecialidades.value || _errorEspecialidades.value.isNotBlank() ||
                formulario.especialidades.any { id -> _especialidades.value.none { it.idEspecialidad == id } })) {
            _mensaje.value = "Carga las especialidades y selecciona las que ofreces."
            return
        }
        _cargando.value = true
        _mensaje.value = ""
        viewModelScope.launch {
            try {
                val respuesta = enviar(formulario.solicitud())
                if (respuesta != null) {
                    _formulario.value = formulario.copy(correo = respuesta.usuario.correo, password = "", confirmacion = "")
                    _registrado.value = true
                } else {
                    _mensaje.value = "No se pudo crear la cuenta. Verifica tus datos; si ya usaste este correo, inicia sesión."
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _mensaje.value = "No pudimos confirmar el registro. Revisa tu conexión. Si la cuenta se creó, puedes intentar iniciar sesión."
            } finally {
                _cargando.value = false
            }
        }
    }
}
