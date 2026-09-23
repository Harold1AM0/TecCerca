package com.example.teccerca.data.remoto


object ApiServicio {
    val usuarioApi: UsuarioApi =
        ConfiguracionApi.retrofit.create(UsuarioApi::class.java)

    val tecnicoApi: TecnicoApi =
        ConfiguracionApi.retrofit.create(TecnicoApi::class.java)

    val solicitudApi: SolicitudApi =
        ConfiguracionApi.retrofit.create(SolicitudApi::class.java)


}