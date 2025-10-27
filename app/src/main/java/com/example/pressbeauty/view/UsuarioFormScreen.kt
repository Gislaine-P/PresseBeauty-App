package com.example.pressbeauty.view

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pressbeauty.viewmodel.UsuarioViewModel

@Composable
fun UsuarioFormScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFFFF5F3), Color.White)
                )
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Crear cuenta",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color(0xFFB06F6F)
        )

        Campo(label = "Nombre", valor = estado.nombre, onChange = viewModel::onNombreChange, error = estado.errores.nombre)
        Campo(label = "Apellido", valor = estado.apellido, onChange = viewModel::onApellidoChange, error = estado.errores.apellido)
        Campo(label = "Correo", valor = estado.correo, onChange = viewModel::onCorreoChange, error = estado.errores.correo)
        Campo(label = "Clave", valor = estado.clave, onChange = viewModel::onClaveChange, esClave = true, error = estado.errores.clave)
        Campo(label = "Repetir Clave", valor = estado.repClave, onChange = viewModel::onRepClaveChange, esClave = true, error = estado.errores.repClave)
        Campo(label = "Dirección", valor = estado.direccion, onChange = viewModel::onDireccionChange, error = estado.errores.direccion)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = estado.aceptaTerminos, onCheckedChange = viewModel::onAceptarTerminosChange)
            Text("Acepto los términos y condiciones", color = Color.DarkGray)
        }

        Button(
            onClick = {
                if (viewModel.validarFormulario()) {
                    viewModel.guardarUsuario()
                    navController.navigate("InicioCatalogoScreen")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4B4B4)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Registrar", color = Color.White)
        }

        OutlinedButton(
            onClick = { navController.navigate("LoginScreen") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFB06F6F))
        ) {
            Text("Volver al login")
        }
    }
}

@Composable
fun Campo(
    label: String,
    valor: String,
    onChange: (String) -> Unit,
    esClave: Boolean = false,
    error: String?
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onChange,
        label = { Text(label) },
        visualTransformation = if (esClave) PasswordVisualTransformation() else VisualTransformation.None,
        isError = error != null,
        supportingText = { error?.let { Text(it, color = Color.Red, fontSize = 12.sp) } },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFF4B4B4),
            cursorColor = Color(0xFFB06F6F),
            focusedLabelColor = Color(0xFFB06F6F)
        )
    )
}
