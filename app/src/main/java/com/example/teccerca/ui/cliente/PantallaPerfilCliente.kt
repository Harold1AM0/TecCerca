package com.example.teccerca.ui.cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teccerca.data.modelo.Usuario

// Tipo de archivo: Kotlin file que contiene una función @Composable de nivel superior
private val TealPrimary = Color(0xFF009688)

@Composable
fun PantallaPerfilCliente(
    usuario: Usuario,
    numeroReparaciones: Int,
    miembroDesde: String,
    onVolver: () -> Unit,
    onEditarPerfil: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        // Barra superior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVolver) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Text(
                text = "Perfil",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Avatar
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(Color(0xFFB2DFDB), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = TealPrimary,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${usuario.nombre} ${usuario.apellidos}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Verificado",
                    tint = TealPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(text = "Cliente TecCerca", fontSize = 13.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(16.dp))

            // Tarjetas de estadísticas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                EstadisticaCard(
                    valor = numeroReparaciones.toString(),
                    etiqueta = "REPARACIONES",
                    modifier = Modifier.weight(1f)
                )
                EstadisticaCard(
                    valor = miembroDesde,
                    etiqueta = "MIEMBRO DESDE",
                    valorColor = TealPrimary,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "INFORMACIÓN PERSONAL",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column {
                    FilaInformacion(Icons.Default.Person, "NOMBRE", usuario.nombre)
                    Divider()
                    FilaInformacion(Icons.Default.Badge, "APELLIDOS", usuario.apellidos)
                    Divider()
                    FilaInformacion(Icons.Default.Email, "CORREO ELECTRÓNICO", usuario.correo)
                    Divider()
                    FilaInformacion(Icons.Default.Cake, "EDAD", "${usuario.edad} años")
                }
            }
        }

        Button(
            onClick = onEditarPerfil,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
        ) {
            Text("Editar Perfil")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
private fun EstadisticaCard(
    valor: String,
    etiqueta: String,
    valorColor: Color = Color.Black,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = valor, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = valorColor)
            Text(text = etiqueta, fontSize = 10.sp, color = Color.Gray)
        }
    }
}

@Composable
private fun FilaInformacion(icono: androidx.compose.ui.graphics.vector.ImageVector, etiqueta: String, valor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icono, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = etiqueta, fontSize = 10.sp, color = Color.Gray)
            Text(text = valor, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}