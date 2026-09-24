package com.example.teccerca.ui.autenticacion

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.teccerca.ui.componentes.*

@Composable
private fun CampoRegistro(
    etiqueta: String, valor: String, onCambio: (String) -> Unit,
    error: String?, habilitado: Boolean,
    tipo: KeyboardType = KeyboardType.Text, secreto: Boolean = false,
    variasLineas: Boolean = false, ayuda: String? = null
) {
    var visible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = valor, onValueChange = onCambio, label = { Text(etiqueta) },
        enabled = habilitado, isError = error != null, singleLine = !variasLineas,
        minLines = if (variasLineas) 3 else 1,
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = tipo),
        visualTransformation = if (secreto && !visible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = if (secreto) ({
            IconButton(onClick = { visible = !visible }) {
                Icon(if (visible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                    if (visible) "Ocultar $etiqueta" else "Mostrar $etiqueta")
            }
        }) else null,
        supportingText = if (error != null || ayuda != null) ({ Text(error ?: ayuda.orEmpty()) }) else null,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
        )
    )
}

@Composable
fun RegistroPantalla(
    onVolver: () -> Unit,
    onIrLogin: (String) -> Unit,
    registroViewModel: RegistroViewModel = viewModel()
) {
    val formulario by registroViewModel.formulario
    val errores by registroViewModel.errores
    val cargando by registroViewModel.cargando
    val registrado by registroViewModel.registrado
    val mensaje by registroViewModel.mensaje
    BackHandler(enabled = cargando || registrado) {
        if (registrado) onIrLogin(formulario.correo)
    }

    Scaffold(topBar = {
        BarraSuperior(if (registrado) "Cuenta creada" else "Crear cuenta",
            if (cargando) null else if (registrado) ({ onIrLogin(formulario.correo) }) else onVolver)
    }) { padding ->
        LazyColumn(
            Modifier.fillMaxSize().padding(padding).imePadding().testTag("registro"),
            contentPadding = PaddingValues(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (registrado) {
                item {
                    Column(Modifier.fillMaxWidth().padding(top = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.primaryContainer) {
                            Icon(Icons.Outlined.CheckCircle, null, Modifier.padding(20.dp).size(48.dp), tint = MaterialTheme.colorScheme.primary)
                        }
                        Text("¡Tu cuenta está lista!", style = MaterialTheme.typography.headlineMedium)
                        Text("Ya puedes iniciar sesión con ${formulario.correo}.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                        if (formulario.idRol == 2) Text(
                            "Tu perfil de técnico se creó sin disponibilidad activa.",
                            style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        BotonPrincipal("Ir a iniciar sesión", { onIrLogin(formulario.correo) })
                    }
                }
            } else {
                item {
                    Text("Únete a TecCerca", style = MaterialTheme.typography.headlineMedium)
                    Text("Encuentra ayuda o comparte lo que sabes hacer.", color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp))
                }
                item {
                    Text("¿Cómo quieres usar TecCerca?", style = MaterialTheme.typography.titleSmall)
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        FilterChip(
                            selected = formulario.idRol == 1, enabled = !cargando,
                            onClick = { registroViewModel.actualizar(formulario.copy(idRol = 1)) }, label = { Text("Soy cliente") }
                        )
                        FilterChip(
                            selected = formulario.idRol == 2, enabled = !cargando,
                            onClick = { registroViewModel.actualizar(formulario.copy(idRol = 2)) }, label = { Text("Soy técnico") }
                        )
                    }
                }
                item { CampoRegistro("Nombre completo", formulario.nombre,
                    { registroViewModel.actualizar(formulario.copy(nombre = it)) }, errores["nombre"], !cargando) }
                item { CampoRegistro("Correo electrónico", formulario.correo,
                    { registroViewModel.actualizar(formulario.copy(correo = it)) }, errores["correo"], !cargando, KeyboardType.Email) }
                item { CampoRegistro("Teléfono (opcional)", formulario.telefono,
                    { registroViewModel.actualizar(formulario.copy(telefono = it)) }, errores["telefono"], !cargando, KeyboardType.Phone) }
                item { CampoRegistro("Contraseña", formulario.password,
                    { registroViewModel.actualizar(formulario.copy(password = it)) }, errores["password"], !cargando,
                    KeyboardType.Password, secreto = true, ayuda = "Al menos 8 caracteres.") }
                item { CampoRegistro("Confirmar contraseña", formulario.confirmacion,
                    { registroViewModel.actualizar(formulario.copy(confirmacion = it)) }, errores["confirmacion"], !cargando,
                    KeyboardType.Password, secreto = true) }
                if (formulario.idRol == 2) {
                    item { HorizontalDivider(); Text("Tu perfil profesional", Modifier.padding(top = 16.dp), style = MaterialTheme.typography.titleMedium) }
                    item { CampoRegistro("Años de experiencia (opcional)", formulario.experiencia,
                        { registroViewModel.actualizar(formulario.copy(experiencia = it)) }, errores["experiencia"], !cargando, KeyboardType.Number) }
                    item { CampoRegistro("Servicios que ofreces", formulario.descripcion,
                        { registroViewModel.actualizar(formulario.copy(descripcion = it)) }, errores["descripcion"], !cargando, variasLineas = true) }
                    item { Text("Especialidades", style = MaterialTheme.typography.titleSmall) }
                    when {
                        registroViewModel.cargandoEspecialidades.value -> item {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                CircularProgressIndicator(Modifier.size(24.dp))
                                Text("Cargando especialidades…")
                            }
                        }
                        registroViewModel.errorEspecialidades.value.isNotBlank() -> item {
                            EstadoCliente("No pudimos cargar las especialidades", "Revisa tu conexión y vuelve a intentarlo.",
                                "Intentar nuevamente", registroViewModel::cargarEspecialidades)
                        }
                        registroViewModel.especialidades.value.isEmpty() -> item {
                            EstadoCliente("No hay especialidades disponibles", "Todavía no puedes completar el registro como técnico.",
                                "Actualizar", registroViewModel::cargarEspecialidades)
                        }
                        else -> items(registroViewModel.especialidades.value, key = { it.idEspecialidad }) { especialidad ->
                            FilterChip(
                                selected = especialidad.idEspecialidad in formulario.especialidades,
                                enabled = !cargando,
                                onClick = {
                                    val ids = if (especialidad.idEspecialidad in formulario.especialidades)
                                        formulario.especialidades - especialidad.idEspecialidad else formulario.especialidades + especialidad.idEspecialidad
                                    registroViewModel.actualizar(formulario.copy(especialidades = ids))
                                },
                                label = { Text(especialidad.nombre) }
                            )
                        }
                    }
                    if (errores["especialidades"] != null) item {
                        Text(errores.getValue("especialidades"), color = MaterialTheme.colorScheme.error)
                    }
                }
                if (mensaje.isNotBlank()) item { Text(mensaje, color = MaterialTheme.colorScheme.error) }
                item {
                    BotonPrincipal("Crear cuenta", registroViewModel::registrar, cargando = cargando,
                        habilitado = formulario.idRol == 1 ||
                            (!registroViewModel.cargandoEspecialidades.value && registroViewModel.errorEspecialidades.value.isEmpty() &&
                                registroViewModel.especialidades.value.isNotEmpty()))
                }
                item {
                    TextButton(onClick = onVolver, enabled = !cargando, modifier = Modifier.fillMaxWidth()) {
                        Text("¿Ya tienes cuenta? Inicia sesión")
                    }
                }
            }
        }
    }
}
