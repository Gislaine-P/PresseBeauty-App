package com.example.pressbeauty.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pressbeauty.view.components.BottomNavBar
import com.example.pressbeauty.viewmodel.CarritoViewModel

@Composable
fun CarritoScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel
) {
    val carrito by carritoViewModel.carrito.collectAsState()


    Scaffold(
        bottomBar = { BottomNavBar(navController) } // Aquí agregas la barra de navegación


    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            AnimatedVisibility(visible = carrito.productos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tu carrito está vacío 🛍️", style = MaterialTheme.typography.titleMedium)
                }
            }

            AnimatedVisibility(visible = carrito.productos.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(carrito.productos) { detalle ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = detalle.nombreProducto),
                                    contentDescription = detalle.nombreProducto,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .padding(4.dp),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(detalle.nombreProducto, fontWeight = FontWeight.SemiBold)
                                    Text("Precio: ${detalle.precioUnitario}")
                                    Text("Subtotal: ${detalle.subtotalCarrito}")
                                }

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Button(
                                        onClick = { carritoViewModel.aumentarCantidad(detalle.idProducto) },
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Text("+")
                                    }
                                    Text(detalle.cantidadProducto.toString())
                                    Button(
                                        onClick = { carritoViewModel.disminuirCantidad(detalle.idProducto) },
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Text("-")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(visible = carrito.productos.isNotEmpty()) {
                Column {
                    Text(
                        text = "Total: ${carrito.total}",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            carritoViewModel.limpiarCarrito()
                            navController.navigate("CarritoScreen")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Finalizar compra")
                    }
                }
            }
        }
    }
}
