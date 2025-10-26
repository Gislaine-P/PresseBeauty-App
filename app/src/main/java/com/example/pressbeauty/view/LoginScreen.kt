package com.example.pressbeauty.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pressbeauty.viewmodel.LoginViewModel
import com.example.pressbeauty.viewmodel.UsuarioViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
    usuarioViewModel: UsuarioViewModel
) {
    val estado2 by loginViewModel.estado2.collectAsState()
    var mostrarAlerta by remember { mutableStateOf(false) }
    var mensajeAlerta by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        loginViewModel.onNombreChange("")
        loginViewModel.onClaveChange("")
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFF4A6C1),
                            Color(0xFFD19FE0),
                            Color(0xFFF06292),
                            Color(0xFFFFB07C)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(1000f, 1000f)
                    )
                )
        )

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = estado2.nombre,
                onValueChange = loginViewModel::onNombreChange,
                label = { Text("Nombre de Usuario") },
                isError = estado2.errores2.nombre != null,
                supportingText = {
                    estado2.errores2.nombre?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = estado2.clave,
                onValueChange = loginViewModel::onClaveChange,
                label = { Text("Contraseña") },
                isError = estado2.errores2.clave != null,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (loginViewModel.validarFormulario()) {
                        usuarioViewModel.iniciarSesion(
                            nombre = estado2.nombre,
                            clave = estado2.clave
                        ) { exito ->
                            if (exito) {
                                navController.navigate("PerfilUsuarioScreen")
                            } else {
                                mensajeAlerta = "Usuario o contraseña incorrectos."
                                mostrarAlerta = true
                            }
                        }
                    } else {
                        mensajeAlerta = "Por favor completa todos los campos antes de iniciar sesión."
                        mostrarAlerta = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Text("Iniciar sesión")
            }

            if (mostrarAlerta) {
                AlertDialog(
                    onDismissRequest = { mostrarAlerta = false },
                    confirmButton = {
                        TextButton(onClick = { mostrarAlerta = false }) {
                            Text("Aceptar", color = Color(0xFF6A1B9A))
                        }
                    },
                    title = { Text("Inicio de sesión fallido") },
                    text = { Text(mensajeAlerta) }
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("¿No tienes cuenta? ")
                Text(
                    text = "Regístrate",
                    color = Color(0xFF6A1B9A),
                    modifier = Modifier.clickable {
                        navController.navigate("UsuarioFormScreen")
                    }
                )
            }
        }
    }
}



