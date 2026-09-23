package com.example.teccerca.ui.autenticacion

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teccerca.data.modelo.UsuarioLogin
import com.example.teccerca.data.repositorio.UsuarioRepositorio
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State


class AutenticacionViewModel : ViewModel() {


    private val repositorio = UsuarioRepositorio()



    private val _mensaje = mutableStateOf("")

    val mensaje: State<String> = _mensaje



    private val _cargando = mutableStateOf(false)

    val cargando: State<Boolean> = _cargando



    fun iniciarSesion(
        correo:String,
        password:String,
        onResultado:(Int?) -> Unit
    ) {


        viewModelScope.launch {


            _cargando.value = true


            try {


                println("ENVIANDO LOGIN")
                println("CORREO: $correo")



                val respuesta = repositorio.iniciarSesion(

                    UsuarioLogin(
                        correo,
                        password
                    )

                )



                println("RESPUESTA LOGIN: $respuesta")



                if(respuesta != null){


                    _mensaje.value = respuesta.mensaje


                    onResultado(

                        respuesta.usuario.idRol

                    )


                }else{


                    _mensaje.value =
                        "Correo o contraseña incorrectos"


                    onResultado(null)

                }



            } catch (e: Exception) {


                Log.e(

                    "AutenticacionViewModel",

                    "Error al iniciar sesión",

                    e

                )



                _mensaje.value =
                    "Error de conexión, intenta de nuevo"



                onResultado(null)



            } finally {


                _cargando.value = false


            }


        }

    }


}