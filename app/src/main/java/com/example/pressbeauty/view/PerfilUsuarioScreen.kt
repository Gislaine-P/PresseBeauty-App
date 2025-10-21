package com.example.pressbeauty.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pressbeauty.viewmodel.UsuarioViewModel


@Composable
fun PerfilUsuarioScreen(viewModel: UsuarioViewModel){
    val estado by viewModel.estado.collectAsState()

    Box(
    modifier = Modifier
    .fillMaxSize()
    .background(Color(0xFFF3E5F5))
    .padding(16.dp),
    contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Bienvenid@, estos son tus datos:",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6A1B9A)
                    )
                )

                Divider(color = Color(0xFFB39DDB), thickness = 1.dp)

                Text("👤 Nombre: ${estado.nombre}", fontSize = 18.sp)
                Text("👥 Apellido: ${estado.apellido}", fontSize = 18.sp)
                Text("✉️ Correo: ${estado.correo}", fontSize = 18.sp)
                Text("🏠 Dirección: ${estado.direccion}", fontSize = 18.sp)
                Text(
                    text = "📜 Términos aceptados: " +
                            if (estado.aceptaTerminos) "✅ Aceptados" else "❌ Declinados",
                    fontSize = 18.sp,
                    color = if (estado.aceptaTerminos) Color(0xFF388E3C) else Color(0xFFD32F2F)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Podés volver al login si querés */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6A1B9A),
                        contentColor = Color.White
                    )
                ) {
                    Text("Volver al login")
                }
            }
        }
    }
    }
