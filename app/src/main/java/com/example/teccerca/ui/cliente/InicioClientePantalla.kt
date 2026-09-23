package com.example.teccerca.ui.cliente

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun InicioClientePantalla(){

    val clienteViewModel: ClienteViewModel = viewModel()


    TecnicosCercanosPantalla(
        clienteViewModel = clienteViewModel
    )

}