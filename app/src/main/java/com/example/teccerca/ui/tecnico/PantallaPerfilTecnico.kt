package com.example.teccerca.ui.tecnico

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
import com.example.teccerca.data.modelo.PerfilTecnico

// Tipo de archivo: Kotlin file que contiene una función @Composable de nivel superior
private val TealPrimary = Color(0xFF009688)

@Composable
fun PantallaPerfilTecnico(
    perfil: PerfilTecnico,
    onVolver: () -> Unit,
    onContactar: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

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
                text = "Perfil Técnico",
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

            Box(contentAlignment = Alignment.BottomEnd) {
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
                if (perfil.estaActivo) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(Color(0xFF4CAF50), CircleShape)
                    )
                }
            }

            if (perfil.estaActivo) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Activo", fontSize = 11.sp, color = Color(0xFF4CAF50))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${perfil.nombre} ${perfil.apellido}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            if (perfil.esEspecialistaVerificado) {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .background(Color(0xFFE0F2F1), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Especialista Verificado",
                        fontSize = 11.sp,
                        color = TealPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(5) { index ->
                    Icon(
                        imageVector = if (index < perfil.calificacionPromedio.toInt()) Icons.Default.Star
                        else Icons.Default.StarBorder,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${perfil.calificacionPromedio} de 5",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "(${perfil.numeroOpiniones} opiniones)",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "INFORMACIÓN PROFESIONAL",
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
                    FilaInformacionTecnico(Icons.Default.Person, "NOMBRE", perfil.nombre)
                    Divider()
                    FilaInformacionTecnico(Icons.Default.Badge, "APELLIDO", perfil.apellido)
                    Divider()
                    FilaInformacionTecnico(Icons.Default.Cake, "EDAD", "${perfil.edad} años")
                    Divider()
                    FilaInformacionTecnico(Icons.Default.Email, "CORREO ELECTRÓNICO", perfil.correoElectronico)
                    Divider()
                    FilaInformacionTecnico(Icons.Default.Build, "ESPECIALIDAD", perfil.especialidad)
                }
            }
        }

        Button(
            onClick = onContactar,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
        ) {
            Text("Contactar")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
private fun FilaInformacionTecnico(icono: androidx.compose.ui.graphics.vector.ImageVector, etiqueta: String, valor: String) {
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