package com.example.pressbeauty.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pressbeauty.viewmodel.LoginViewModel


@Composable
fun LoginScreen(
    navController : NavController,
    viewModel: LoginViewModel
){
    val estado2 by viewModel.estado2.collectAsState()

    Box(modifier = Modifier.fillMaxSize()
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFF4A6C1),
                    Color(0xFFD19FE0),
                    Color(0xFFF06292),
                    Color(0xFFFFB07C)
                ),
                start = Offset(0f, 0f),
                end = Offset(1000f, 1000f))
            )
        )
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally



        ){
            OutlinedTextField(
                value = estado2.nombre,
                onValueChange = viewModel::onNombreChange,
                label = { Text("Nombre de Usuario") },
                isError = estado2.errores2.nombre != null,
                supportingText = {
                    estado2.errores2.nombre?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.White,
                    unfocusedBorderColor = Color(0xFFB0BEC5),
                    focusedBorderColor = Color(0xFF6A1B9A),
                    cursorColor = Color(0xFF6A1B9A),
                    focusedLabelColor = Color(0xFF6A1B9A)
                )
            )
            OutlinedTextField(
                value = estado2.clave,
                onValueChange = viewModel::onClaveChange,
                label={Text("Contraseña")},
                isError = estado2.errores2.clave!=null,
                supportingText = {
                    estado2.errores2.clave?.let{
                        Text(it,color = MaterialTheme.colorScheme.error)
                    }
                },modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.White,
                    unfocusedBorderColor = Color(0xFFB0BEC5),
                    focusedBorderColor = Color(0xFF6A1B9A),
                    cursorColor = Color(0xFF6A1B9A),
                    focusedLabelColor = Color(0xFF6A1B9A)
                )
            )
            Button(
                onClick = { navController.navigate("PerfilUsuarioScreen") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Text("Iniciar sesión")
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

