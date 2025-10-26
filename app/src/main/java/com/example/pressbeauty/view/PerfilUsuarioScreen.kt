package com.example.pressbeauty.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pressbeauty.view.components.BottomNavBar
import com.example.pressbeauty.viewmodel.UsuarioViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PerfilUsuarioScreen(viewModel: UsuarioViewModel, navController: NavController) {
    val estado by viewModel.estado.collectAsState()
    val imagenUri by viewModel.imagenUri.collectAsState()
    val context = LocalContext.current

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> viewModel.setImage(uri) }

    var cameraUri by remember { mutableStateOf<Uri?>(null) }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success -> if (success) viewModel.setImage(cameraUri) }

    fun createImageUri(context: Context): Uri {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir)
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    val requestCameraPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val uri = createImageUri(context)
            cameraUri = uri
            takePictureLauncher.launch(uri)
        }
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFFFFECDC), Color(0xFFFFFAF8))
                    )
                )
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Imagen circular
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .shadow(8.dp, CircleShape)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = imagenUri
                            ?: "https://i.pinimg.com/736x/0c/28/b9/0c28b934fc773ed7c9d35b829d5356b9.jpg"
                    ),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "${estado.nombre} ${estado.apellido}",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1C1C)
            )

            Text(
                text = estado.correo,
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botones para imagen
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { pickImageLauncher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4F7A)),
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Galería", color = Color.White)
                }

                Button(
                    onClick = {
                        when (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        )) {
                            PackageManager.PERMISSION_GRANTED -> {
                                val uri = createImageUri(context)
                                cameraUri = uri
                                takePictureLauncher.launch(uri)
                            }
                            else -> requestCameraPermission.launch(Manifest.permission.CAMERA)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4F7A)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cámara", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Datos del usuario
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Dirección: ${estado.direccion}", fontSize = 18.sp)
                    Text(
                        "Términos: " +
                                if (estado.aceptaTerminos) "Aceptados" else "No aceptados",
                        fontSize = 18.sp,
                        color = if (estado.aceptaTerminos) Color(0xFF388E3C) else Color(0xFFD32F2F)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Editar Datos")
            }
            Button(
                onClick = { navController.graph.startDestinationId  },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4F7A)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}
