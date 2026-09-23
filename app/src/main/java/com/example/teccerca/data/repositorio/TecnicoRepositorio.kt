package com.example.teccerca.data.repositorio

import com.example.teccerca.data.modelo.UbicacionRequest
import com.example.teccerca.data.remoto.ApiServicio
import com.example.teccerca.data.respuesta.TecnicoRespuesta

class TecnicoRepositorio {

    private val api = ApiServicio.tecnicoApi

    suspend fun obtenerTecnicosCercanos(
        ubicacion: UbicacionRequest
    ): List<TecnicoRespuesta>? {

        return try {

            val respuesta = api.obtenerTecnicosCercanos(
                ubicacion
            )

            if(respuesta.isSuccessful){

                respuesta.body()

            }else{

                null

            }

        }catch(e: Exception){

            throw e

        }

    }

}