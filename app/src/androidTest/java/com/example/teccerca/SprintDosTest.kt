package com.example.teccerca

import android.graphics.Bitmap
import android.os.Parcel
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.platform.app.InstrumentationRegistry
import com.example.teccerca.data.modelo.Especialidad
import com.example.teccerca.data.modelo.Solicitud
import com.example.teccerca.data.respuesta.*
import com.example.teccerca.ui.cliente.*
import com.example.teccerca.ui.theme.TecCercaTheme
import kotlinx.coroutines.CompletableDeferred
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.io.File

class SprintDosTest {
    @get:Rule val compose = createComposeRule()
    private val tecnico = TecnicoRespuesta(2, "Carlos Ramírez", "988888888", 5,
        "Especialista en reparación de laptops y computadoras.", "4.8", 0.03, listOf("Computadoras"))

    private fun captura(nombre: String) {
        compose.waitForIdle()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val carpeta = context.getExternalFilesDir("sprint2-qa")!!
        carpeta.mkdirs()
        File(carpeta, "$nombre.png").outputStream().use {
            compose.onRoot().captureToImage().asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }

    @Test fun homeAgrupaTecnicosYAbreBusqueda() {
        var busqueda = false
        lateinit var vm: ClienteViewModel
        compose.setContent {
            vm = remember {
                ClienteViewModel(SavedStateHandle(),
                    obtenerCategorias = { listOf(Especialidad(1, "Computadoras"), Especialidad(2, "Electricidad")) },
                    obtenerTecnicos = { listOf(tecnico) })
            }
            TecCercaTheme {
                InicioClientePantalla("Pedro", vm, { busqueda = true }, {}, {}, {})
            }
        }
        compose.waitUntil { !vm.cargando.value }
        compose.runOnIdle {
            assertEquals(1, vm.tecnicos.value.size)
            assertEquals(listOf("Computadoras", "Electricidad"), vm.tecnicos.value.first().especialidades)
        }
        compose.onNodeWithText("Hola, Pedro 👋").assertIsDisplayed()
        captura("01-home")
        compose.onNodeWithTag("home").performScrollToNode(hasText("Carlos Ramírez"))
        captura("02-home-tecnicos")
        compose.onNodeWithTag("home").performScrollToNode(hasText("Computadoras"))
        compose.onNodeWithText("Computadoras", useUnmergedTree = true).performClick()
        compose.runOnIdle { assertTrue(busqueda); assertEquals("Computadoras", vm.categoria.value) }
    }

    @Test fun listaMuestraVacioYPermiteLimpiarBusqueda() {
        lateinit var vm: ClienteViewModel
        compose.setContent {
            vm = remember { ClienteViewModel(SavedStateHandle(), { listOf(Especialidad(1, "Computadoras")) }, { listOf(tecnico) }) }
            TecCercaTheme { TecnicosCercanosPantalla(vm, {}, {}) }
        }
        compose.waitUntil { !vm.cargando.value }
        captura("03-lista")
        compose.onNodeWithText("Buscar técnico o servicio…").performTextInput("plomería")
        compose.onNodeWithTag("lista").performScrollToNode(hasText("Limpiar filtros"))
        compose.onNodeWithText("Sin resultados para tu búsqueda").assertIsDisplayed()
        compose.onNodeWithText("Limpiar filtros").performClick()
        compose.runOnIdle { assertEquals(1, vm.resultados.size) }
    }

    @Test fun perfilAbreSolicitud() {
        var solicitado = false
        compose.setContent { TecCercaTheme { DetalleTecnicoPantalla(tecnico, {}, { solicitado = true }) } }
        compose.onAllNodesWithText("Carlos Ramírez")[0].assertIsDisplayed()
        captura("04-perfil")
        compose.onNodeWithText("Solicitar servicio").performClick()
        compose.runOnIdle { assertTrue(solicitado) }
    }

    @Test fun formularioValidaYConfirmaSoloTrasRespuesta() {
        val respuesta = CompletableDeferred<SolicitudRespuesta?>()
        var llamadas = 0
        var descripcionRecibida = ""
        lateinit var vm: SolicitudViewModel
        compose.setContent {
            var confirmada by remember { mutableStateOf<Int?>(null) }
            vm = remember {
                SolicitudViewModel(SavedStateHandle()) { request ->
                    llamadas++
                    descripcionRecibida = request.descripcion
                    assertEquals(1, request.idCliente)
                    assertEquals(2, request.idTecnico)
                    respuesta.await()
                }
            }
            TecCercaTheme {
                if (confirmada == null) SolicitudPantalla(2, tecnico.nombre, 1, {}, { confirmada = it }, vm)
                else ConfirmacionSolicitudPantalla(tecnico.nombre, confirmada!!, {})
            }
        }
        compose.onNodeWithText("Enviar solicitud").performScrollTo().assertIsNotEnabled()
        compose.onNodeWithText("Describe tu problema").performTextInput("  La laptop no enciende  ")
        captura("05-solicitud")
        compose.onNodeWithText("Enviar solicitud").performScrollTo().performClick()
        compose.runOnIdle {
            assertTrue(vm.cargando.value)
            vm.crearSolicitud(1, 2, "No debe enviarse dos veces")
            assertEquals(1, llamadas)
            assertEquals("La laptop no enciende", descripcionRecibida)
            respuesta.complete(SolicitudRespuesta("Solicitud creada correctamente", Solicitud(42, 1, 2, descripcionRecibida, "PENDIENTE", "")))
        }
        compose.waitUntil { vm.idSolicitud.value == 42 }
        compose.onNodeWithText("Solicitud enviada\ncorrectamente").assertIsDisplayed()
        captura("06-confirmacion")
    }

    @Test fun erroresNoGeneranConfirmacionYPermitenReintentar() {
        lateinit var vm: SolicitudViewModel
        var intentos = 0
        compose.setContent {
            vm = remember {
                SolicitudViewModel(SavedStateHandle()) {
                    intentos++
                    if (intentos == 1) null else throw java.io.IOException("Sin conexión")
                }
            }
            TecCercaTheme { SolicitudPantalla(2, tecnico.nombre, 1, {}, {}, vm) }
        }
        compose.runOnIdle { vm.crearSolicitud(1, 2, "Pantalla rota") }
        compose.waitUntil { !vm.cargando.value && intentos == 1 }
        compose.runOnIdle {
            assertNull(vm.idSolicitud.value)
            assertTrue(vm.mensaje.value.contains("No se pudo"))
            vm.crearSolicitud(1, 2, "Pantalla rota")
        }
        compose.waitUntil { !vm.cargando.value && intentos == 2 }
        compose.runOnIdle {
            assertNull(vm.idSolicitud.value)
            assertTrue(vm.mensaje.value.contains("No pudimos confirmar"))
        }
    }

    @Test fun cuentaSinClienteNoPuedeEnviar() {
        compose.setContent { TecCercaTheme { SolicitudPantalla(2, tecnico.nombre, null, {}, {}) } }
        compose.onNodeWithText("Describe tu problema").performTextInput("Pantalla rota")
        compose.onNodeWithText("Enviar solicitud").performScrollTo().assertIsNotEnabled()
    }

    @Test fun perfilClienteNavegaYCierraSesionConDatosReales() {
        var cercanos = false
        var cerrada = false
        compose.setContent {
            TecCercaTheme {
                com.example.teccerca.ui.perfil.PerfilClientePantalla(
                    "María González", "maria@example.com", {}, {}, { cercanos = true }, { cerrada = true }
                )
            }
        }
        compose.onNodeWithText("maria@example.com").assertIsDisplayed()
        captura("07-perfil-cliente")
        compose.onNodeWithText("Cercanos").performClick()
        compose.runOnIdle { assertTrue(cercanos) }
        compose.onNodeWithText("Cerrar sesión").performScrollTo().performClick()
        compose.runOnIdle { assertTrue(cerrada) }
    }

    @Test fun loginMantieneRegistroAccesibleSinCredenciales() {
        var registro = false
        compose.setContent {
            TecCercaTheme {
                com.example.teccerca.ui.autenticacion.LoginPantalla(irCliente = {}, irTecnico = {}, irRegistro = { registro = true })
            }
        }
        compose.onNodeWithText("Iniciar sesión").performScrollTo().assertIsNotEnabled()
        captura("00-login")
        compose.onNodeWithText("Registrarse").performScrollTo().performClick()
        compose.runOnIdle { assertTrue(registro) }
    }
    @Test fun tecnicoConEspecialidadesSobreviveParcel() {
        val parcel = Parcel.obtain()
        try {
            parcel.writeParcelable(tecnico, 0)
            parcel.setDataPosition(0)
            @Suppress("DEPRECATION")
            val copia = parcel.readParcelable<TecnicoRespuesta>(TecnicoRespuesta::class.java.classLoader)
            assertEquals(tecnico, copia)
        } finally { parcel.recycle() }
    }
}
