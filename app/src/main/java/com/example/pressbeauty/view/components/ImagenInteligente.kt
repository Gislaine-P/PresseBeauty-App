package com.example.pressbeauty.view.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ImagenInteligente(imagenUri: Uri?) {
    if (imagenUri != null) {
        Image(
            painter = rememberAsyncImagePainter(model = imagenUri),
            contentDescription = "Imagen de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
        )
    } else {
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = "Icono de perfil por defecto",
            tint = Color.White,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = 0.2f))
        )
    }
}
