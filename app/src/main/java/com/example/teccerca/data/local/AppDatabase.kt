package com.example.teccerca.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.teccerca.data.modelo.Usuario

// Tipo de archivo: Kotlin file con una clase abstracta (abstract class) que extiende RoomDatabase

@Database(
    entities = [Usuario::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCIA: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Patrón Singleton: evita crear más de una instancia de la base de datos
            return INSTANCIA ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "teccerca_database"
                ).build()
                INSTANCIA = instancia
                instancia
            }
        }
    }
}