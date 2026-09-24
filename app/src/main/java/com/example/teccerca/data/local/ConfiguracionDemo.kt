package com.example.teccerca.data.local

// Relaciones verificadas mediante una consulta de solo lectura a la BD local.
// Exclusivo para el Sprint 2: el login todavía no entrega idCliente.
// No se usa idUsuario como idCliente ni se asignan solicitudes de cuentas nuevas.
object ConfiguracionDemo {
    private val clientesPorUsuario = mapOf(
        1 to 1, // Juan Pérez
        5 to 2  // Luis Torres
    )

    fun clientePara(usuario: Int): Int? = clientesPorUsuario[usuario]
}
