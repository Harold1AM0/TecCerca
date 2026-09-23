package com.example.teccerca.ui.cliente

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.teccerca.ui.componentes.TarjetaTecnico

@Composable
fun TecnicosCercanosPantalla(
    clienteViewModel: ClienteViewModel = viewModel()
){

    val tecnicos by clienteViewModel.tecnicos
    val mensaje by clienteViewModel.mensaje
    val cargando by clienteViewModel.cargando


    LaunchedEffect(Unit){

        clienteViewModel.cargarTecnicos(
            -12.046500,
            -77.043000
        )

    }


    Column {

        Text(
            text = "Técnicos cercanos"
        )


        if(cargando){

            Text(
                text = "Buscando técnicos..."
            )

        }


        if(mensaje.isNotEmpty()){

            Text(
                text = mensaje
            )

        }


        tecnicos.forEach { tecnico ->

            TarjetaTecnico(
                tecnico = tecnico
            )

        }

    }

}