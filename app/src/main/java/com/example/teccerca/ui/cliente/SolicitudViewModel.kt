package com.example.teccerca.ui.cliente

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teccerca.data.modelo.SolicitudRequest
import com.example.teccerca.data.repositorio.SolicitudRepositorio
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class SolicitudViewModel @JvmOverloads constructor(
    private val estado: SavedStateHandle,
    private val enviarSolicitud: (suspend (SolicitudRequest) -> com.example.teccerca.data.respuesta.SolicitudRespuesta?)? = null
) : ViewModel() {
    private val repositorio = SolicitudRepositorio()

    private val _mensaje = mutableStateOf("")
    val mensaje: State<String> = _mensaje
    private val _cargando = mutableStateOf(false)
    val cargando: State<Boolean> = _cargando
    private val _idSolicitud = mutableStateOf(estado.get<Int>("idSolicitudCreada"))
    val idSolicitud: State<Int?> = _idSolicitud

    fun crearSolicitud(idCliente: Int, idTecnico: Int, descripcion: String) {
        if (_cargando.value || _idSolicitud.value != null) return
        if (idCliente <= 0 || idTecnico <= 0 || descripcion.isBlank()) {
            _mensaje.value = "Completa la descripción y verifica tu cuenta antes de enviar."
            return
        }
        _cargando.value = true
        _mensaje.value = ""
        viewModelScope.launch {
            try {
                val solicitud = SolicitudRequest(idCliente, idTecnico, descripcion.trim())
                val respuesta = (enviarSolicitud ?: repositorio::crearSolicitud)(solicitud)
                if (respuesta != null) {
                    _mensaje.value = respuesta.mensaje
                    estado["idSolicitudCreada"] = respuesta.solicitud.idSolicitud
                    _idSolicitud.value = respuesta.solicitud.idSolicitud
                } else {
                    _mensaje.value = "No se pudo crear la solicitud. Es posible que ya tengas una solicitud activa con este técnico."
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _mensaje.value = "No pudimos confirmar el envío. Revisa tu conexión antes de volver a intentarlo."
            } finally {
                _cargando.value = false
            }
        }
    }
}
