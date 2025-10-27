package com.example.pressbeauty.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFFFF6F5), Color.White))
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB06F6F))

            OutlinedTextField(
                value = estado2.nombre,
                onValueChange = loginViewModel::onNombreChange,
                label = { Text("Nombre de Usuario") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF4B4B4),
                    cursorColor = Color(0xFFB06F6F),
                    focusedLabelColor = Color(0xFFB06F6F)
                )
            )

            OutlinedTextField(
                value = estado2.clave,
                onValueChange = loginViewModel::onClaveChange,
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF4B4B4),
                    cursorColor = Color(0xFFB06F6F),
                    focusedLabelColor = Color(0xFFB06F6F)
                )
            )

            Button(
                onClick = {
                    if (loginViewModel.validarFormulario()) {
                        usuarioViewModel.iniciarSesion(
                            nombre = estado2.nombre,
                            clave = estado2.clave
                        ) { exito ->
                            if (exito) navController.navigate("PerfilUsuarioScreen")
                            else {
                                mensajeAlerta = "Usuario o contraseña incorrectos."
                                mostrarAlerta = true
                            }
                        }
                    } else {
                        mensajeAlerta = "Completa todos los campos antes de iniciar sesión."
                        mostrarAlerta = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4B4B4)),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Iniciar sesión", color = Color.White, fontSize = 17.sp)
            }

            if (mostrarAlerta) {
                AlertDialog(
                    onDismissRequest = { mostrarAlerta = false },
                    confirmButton = {
                        TextButton(onClick = { mostrarAlerta = false }) {
                            Text("Aceptar", color = Color(0xFFB06F6F))
                        }
                    },
                    title = { Text("Error") },
                    text = { Text(mensajeAlerta) }
                )
            }

            Row {
                Text("¿No tienes cuenta? ")
                Text(
                    text = "Regístrate",
                    color = Color(0xFFB06F6F),
                    modifier = Modifier.clickable {
                        navController.navigate("UsuarioFormScreen")
                    }
                )
            }
        }
    }
}
