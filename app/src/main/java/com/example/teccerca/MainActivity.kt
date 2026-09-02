package com.example.teccerca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.teccerca.navegacion.NavegacionApp
import com.example.teccerca.ui.theme.TecCercaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TecCercaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavegacionApp()
                }
            }
        }
    }
}