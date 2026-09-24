package com.example.teccerca.ui.cliente

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teccerca.data.modelo.Especialidad
import com.example.teccerca.data.modelo.UbicacionRequest
import com.example.teccerca.data.repositorio.EspecialidadRepositorio
import com.example.teccerca.data.repositorio.TecnicoRepositorio
import com.example.teccerca.data.respuesta.TecnicoRespuesta
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ClienteViewModel @JvmOverloads constructor(
    private val estado: SavedStateHandle,
    private val obtenerCategorias: suspend () -> List<Especialidad> = EspecialidadRepositorio()::obtenerEspecialidades,
    private val obtenerTecnicos: suspend (UbicacionRequest) -> List<TecnicoRespuesta>? = TecnicoRepositorio()::obtenerTecnicosCercanos
) : ViewModel() {



    private val _tecnicos = mutableStateOf<List<TecnicoRespuesta>>(emptyList())
    val tecnicos: State<List<TecnicoRespuesta>> = _tecnicos
    private val _categorias = mutableStateOf<List<Especialidad>>(emptyList())
    val categorias: State<List<Especialidad>> = _categorias
    private val _mensaje = mutableStateOf("")
    val mensaje: State<String> = _mensaje
    private val _cargando = mutableStateOf(false)
    val cargando: State<Boolean> = _cargando

    var consulta = mutableStateOf(estado.get<String>("consulta").orEmpty())
        private set
    var categoria = mutableStateOf(estado.get<String>("categoria"))
        private set
    var zona = mutableStateOf(estado.get<String>("zona") ?: "Lima centro")
        private set

    val resultados: List<TecnicoRespuesta>
        get() = filtrarTecnicos(_tecnicos.value, consulta.value, categoria.value)

    init { recargar() }

    fun buscar(texto: String) {
        consulta.value = texto
        estado["consulta"] = texto
    }

    fun seleccionarCategoria(nombre: String?) {
        categoria.value = nombre
        estado["categoria"] = nombre
    }

    fun limpiarFiltros() {
        buscar("")
        seleccionarCategoria(null)
    }

    fun usarUbicacion(latitud: Double, longitud: Double) {
        estado["latitud"] = latitud
        estado["longitud"] = longitud
        estado["zona"] = "Tu ubicación"
        zona.value = "Tu ubicación"
        recargar()
    }

    fun recargar() {
        if (_cargando.value) return
        _cargando.value = true
        _mensaje.value = ""
        viewModelScope.launch {
            try {
                val categorias = obtenerCategorias()
                _categorias.value = categorias
                // El endpoint requiere una especialidad. Se combinan sus resultados
                // conservando todas las especialidades y una sola tarjeta por técnico.
                val grupos = coroutineScope {
                    categorias.map { especialidad ->
                        async {
                            val respuesta = obtenerTecnicos(
                                UbicacionRequest(
                                    latitud = estado.get<Double>("latitud") ?: -12.046500,
                                    longitud = estado.get<Double>("longitud") ?: -77.043000,
                                    idEspecialidad = especialidad.idEspecialidad,
                                    radioKm = 5.0
                                )
                            ) ?: error("No se pudo consultar la especialidad")
                            respuesta.map { it.copy(especialidades = listOf(especialidad.nombre)) }
                        }
                    }.awaitAll().flatten()
                }
                _tecnicos.value = grupos.groupBy { it.idTecnico }.values.map { coincidencias ->
                    coincidencias.first().copy(especialidades = coincidencias.flatMap { it.especialidades }.distinct())
                }.sortedBy { it.distanciaKm }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _tecnicos.value = emptyList()
                _mensaje.value = "No pudimos cargar los técnicos. Revisa tu conexión e inténtalo nuevamente."
            } finally {
                _cargando.value = false
            }
        }
    }
}
