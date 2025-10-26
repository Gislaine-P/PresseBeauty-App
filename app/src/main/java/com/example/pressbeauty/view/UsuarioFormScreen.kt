package com.example.pressbeauty.view

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pressbeauty.viewmodel.UsuarioViewModel


@Composable
fun UsuarioFormScreen(
    navController : NavController,
    viewModel: UsuarioViewModel
){
    val estado by viewModel.estado.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ){

        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
        )
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            Arrangement.spacedBy(12.dp)

        ) {
            OutlinedTextField(
                value = estado.nombre,
                onValueChange = viewModel::onNombreChange,
                label = { Text("Nombre") },
                isError = estado.errores.nombre != null,
                supportingText = {
                    estado.errores.nombre?.let {
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
                value = estado.apellido,
                onValueChange = viewModel::onApellidoChange,
                label = { Text("Apellido") },
                isError = estado.errores.apellido != null,
                supportingText = {
                    estado.errores.apellido?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
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
                value = estado.correo,
                onValueChange = viewModel::onCorreoChange,
                label = { Text("Correo") },
                isError = estado.errores.correo != null,
                supportingText = {
                    estado.errores.correo?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
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
                value = estado.clave,
                onValueChange = viewModel::onClaveChange,
                label = { Text("Clave") },
                visualTransformation = PasswordVisualTransformation(),
                isError = estado.errores.clave != null,
                supportingText = {
                    estado.errores.clave?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
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
                value = estado.repClave,
                onValueChange = viewModel::onRepClaveChange,
                label = { Text("Repetir Clave") },
                visualTransformation = PasswordVisualTransformation(),
                isError = estado.errores.repClave != null,
                supportingText = {
                    estado.errores.repClave?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
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
                value = estado.direccion,
                onValueChange = viewModel::onDireccionChange,
                label = { Text("Dirección") },
                isError = estado.errores.direccion != null,
                supportingText = {
                    estado.errores.direccion?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
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

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = estado.aceptaTerminos,
                    onCheckedChange = viewModel::onAceptarTerminosChange
                )
                Spacer(Modifier.width(8.dp))
                Text("Acepto los términos y condiciones")
            }

            Button(
                onClick = {
                    if (viewModel.validarFormulario()) {
                        navController.navigate("InicioCatalogoScreen")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar")
            }
            Button(
                onClick = {
                    navController.navigate("UsuarioFormScreen")
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Volver al login")
            }
        }
    }
}
