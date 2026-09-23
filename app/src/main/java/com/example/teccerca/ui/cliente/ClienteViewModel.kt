package com.example.teccerca.ui.cliente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.teccerca.data.modelo.UbicacionRequest
import com.example.teccerca.data.repositorio.TecnicoRepositorio
import com.example.teccerca.data.respuesta.TecnicoRespuesta
import kotlinx.coroutines.launch

class ClienteViewModel : ViewModel() {

    private val repositorio = TecnicoRepositorio()

    private val _tecnicos = mutableStateOf<List<TecnicoRespuesta>>(emptyList())
    val tecnicos: State<List<TecnicoRespuesta>> = _tecnicos

    private val _mensaje = mutableStateOf("")
    val mensaje: State<String> = _mensaje

    private val _cargando = mutableStateOf(false)
    val cargando: State<Boolean> = _cargando

    fun cargarTecnicos(
        latitud: Double,
        longitud: Double
    ) {
        viewModelScope.launch {

            _cargando.value = true

            try {

                val ubicacion = UbicacionRequest(
                    latitud = latitud,
                    longitud = longitud,
                    idEspecialidad = 1,
                    radioKm = 0.1
                )

                val respuesta = repositorio.obtenerTecnicosCercanos(
                    ubicacion
                )

                if(respuesta != null){

                    _tecnicos.value = respuesta

                }else{

                    _mensaje.value = "No se encontraron técnicos"

                }

            }catch(e: Exception){

                println(
                    "ERROR TECNICOS: ${e.message}"
                )

                _mensaje.value =
                    "Error de conexión, intenta nuevamente"

            }finally{

                _cargando.value = false

            }

        }
    }
}