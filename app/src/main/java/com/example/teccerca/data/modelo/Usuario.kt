package com.example.teccerca.data.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val apellidos: String,
    val correo: String,
    val contrasena: String,
    val edad: String,
    val rol: String, // "Cliente" o "Tecnico"
    val especialidad: String? = null
)