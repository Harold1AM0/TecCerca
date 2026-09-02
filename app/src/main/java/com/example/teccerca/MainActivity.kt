package com.example.teccerca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.teccerca.ui.theme.TecCercaTheme
import com.example.teccerca.ui.autenticacion.PantallaLogin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TecCercaTheme {
                Surface {
                    PantallaLogin()
                }
            }
        }
    }
}