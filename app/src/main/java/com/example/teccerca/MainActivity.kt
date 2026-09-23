package com.example.teccerca


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.teccerca.navegacion.NavegacionApp
import com.example.teccerca.ui.theme.TecCercaTheme


class MainActivity : ComponentActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        setContent {


            TecCercaTheme {


                NavegacionApp()


            }

        }

    }

}