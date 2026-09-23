package com.example.teccerca.data.modelo


data class Solicitud(

    val idSolicitud:Int,

    val idCliente:Int,

    val idTecnico:Int?,

    val descripcion:String,

    val estado:String,

    val fecha:String

)