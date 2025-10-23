package com.example.pressbeauty.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pressbeauty.viewmodel.CarritoViewModel
import com.example.pressbeauty.viewmodel.ProductoViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductoScreen(
    navController: NavController,
    idProducto: String?,
    productoViewModel: ProductoViewModel,
    carritoViewModel: CarritoViewModel
) {
    val productos by productoViewModel.productos.collectAsState()
    var cantidadProd by remember { mutableStateOf(1) }

    val producto = productos.find { it.idProducto == idProducto }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        if (producto != null) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(30.dp)
            ) {
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

                // 🔢 Control de cantidad
                Row {
                    Button(onClick = {
                        if (cantidadProd > 1) cantidadProd--
                    }) {
                        Text("-")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text = cantidadProd.toString())

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = {
                        cantidadProd++
                    }) {
                        Text("+")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 🔘 Botón de volver
                Button(onClick = { navController.popBackStack() }) {
                    Text("Volver atrás")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 🛒 Botón de agregar al carrito
                Button(onClick = {
                    carritoViewModel.agregarProducto(producto, cantidadProd)
                    scope.launch {
                        snackbarHostState.showSnackbar("Producto agregado al carrito")
                    }
                }) {
                    Text("Agregar al carrito")
                }
            }
        } else {
            Text("Producto no encontrado")
        }
    }
}
