package com.example.teccerca.ui.autenticacion

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun LoginPantalla(
    viewModel: AutenticacionViewModel = viewModel(),
    irCliente: () -> Unit,
    irTecnico: () -> Unit,
    irRegistro: () -> Unit
) {


    var correo by remember {
        mutableStateOf("")
    }


    var password by remember {
        mutableStateOf("")
    }


    var mostrarPassword by remember {
        mutableStateOf(false)
    }



    val mensaje by viewModel.mensaje



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Text(
            text = "🔧",
            style = MaterialTheme.typography.displayMedium
        )



        Spacer(
            modifier = Modifier.height(10.dp)
        )



        Text(
            text = "TecCerca",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF009688)
        )



        Text(
            text = "Encuentra técnicos cerca de ti",
            color = Color.Gray
        )



        Spacer(
            modifier = Modifier.height(35.dp)
        )



        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {


            Column(
                modifier = Modifier.padding(20.dp)
            ) {


                Text(
                    text = "Ingresa a tu cuenta",
                    style = MaterialTheme.typography.titleMedium
                )


                Spacer(
                    modifier = Modifier.height(20.dp)
                )



                OutlinedTextField(

                    value = correo,

                    onValueChange = {
                        correo = it
                    },

                    label = {
                        Text("Correo electrónico")
                    },

                    leadingIcon = {
                        Icon(
                            Icons.Default.Email,
                            contentDescription = null
                        )
                    },

                    modifier = Modifier.fillMaxWidth()

                )



                Spacer(
                    modifier = Modifier.height(15.dp)
                )



                OutlinedTextField(

                    value = password,

                    onValueChange = {
                        password = it
                    },

                    label = {
                        Text("Contraseña")
                    },


                    leadingIcon = {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = null
                        )
                    },


                    trailingIcon = {

                        IconButton(
                            onClick = {
                                mostrarPassword = !mostrarPassword
                            }
                        ){

                            Icon(

                                imageVector =
                                    if(mostrarPassword)
                                        Icons.Default.Visibility
                                    else
                                        Icons.Default.VisibilityOff,

                                contentDescription = null
                            )

                        }

                    },


                    visualTransformation =
                        if(mostrarPassword)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),


                    modifier = Modifier.fillMaxWidth()

                )



                Spacer(
                    modifier = Modifier.height(25.dp)
                )

                Button(
                    onClick = {
                        viewModel.iniciarSesion(
                            correo,
                            password
                        ){ rol ->
                            when(rol){
                                1 -> irCliente()
                                2 -> irTecnico()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),

                    colors = ButtonDefaults.buttonColors(

                        containerColor = Color(0xFF009688)

                    ),

                    shape = RoundedCornerShape(12.dp)

                ){

                    Text(
                        "Iniciar Sesión"
                    )

                }



                Spacer(
                    modifier = Modifier.height(15.dp)
                )



                TextButton(
                    onClick = {
                        irRegistro()
                    }
                ){

                    Text(
                        "¿No tienes una cuenta? Registrarse"
                    )

                }



                if(mensaje.isNotEmpty()){


                    Text(

                        text = mensaje,

                        color = Color.Red,

                        modifier = Modifier.padding(top = 10.dp)

                    )

                }


            }


        }


    }


}