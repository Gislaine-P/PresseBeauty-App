package com.example.pressbeauty.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pressbeauty.viewmodel.ProductoViewModel

@Composable
fun ProductoScreen(navController: NavController,
                   idProducto : String?,
                   productoViewModel: ProductoViewModel) {

    val productos by productoViewModel.productos.collectAsState()

    val producto = productos.find { it.idProducto == idProducto }

    if (producto != null) {
        Column(modifier = Modifier.padding(30.dp)) {

                AsyncImage(
                    model = producto.imagenUrl,
                    contentDescription = producto.nombreProducto,
                    modifier = Modifier
                        .size(350.dp)
                        .height(150.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))


                Text(text = "Nombre: ${producto.nombreProducto}")
                Text(text = "Descripción: ${producto.descripcionProducto}")
                Text(text = "Stock: ${producto.stockProducto}")
                Text(text = "Precio: ${producto.precioProducto}")

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { navController.popBackStack() }) {
                    Text("Volver atras")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { navController.popBackStack() }) {
                Text("Agregar al carrito")
                }

        }

    } else {
        Text("Producto no encontrado")
    }
}
