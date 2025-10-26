package com.example.pressbeauty.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pressbeauty.view.components.NavInferior
import com.example.pressbeauty.viewmodel.CarritoViewModel
import kotlinx.coroutines.launch

@Composable
fun CarritoScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel
) {
    val carrito by carritoViewModel.carrito.collectAsState()
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = { NavInferior(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFECDC), Color(0xFFFFFAF8))
                    )
                )
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Text(
                text = "Carrito de Compras",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF4F7A),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 12.dp)
            )

            AnimatedVisibility(visible = carrito.productos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Tu carrito está vacío.",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.Gray,
                            fontSize = 18.sp
                        )
                    )
                }
            }

            AnimatedVisibility(visible = carrito.productos.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(carrito.productos) { detalle ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = detalle.imagenUrl,
                                    contentDescription = detalle.nombreProducto,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        detalle.nombreProducto,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color(0xFF333333)
                                    )
                                    Text(
                                        "Precio: $${detalle.precioUnitario}",
                                        color = Color(0xFF666666),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        "Subtotal: $${detalle.subtotalCarrito}",
                                        color = Color(0xFFFF4F7A),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Button(
                                            onClick = { carritoViewModel.disminuirCantidad(detalle.idProducto) },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4F7A)),
                                            contentPadding = PaddingValues(0.dp),
                                            modifier = Modifier.size(30.dp)
                                        ) { Text("-", color = Color.White) }

                                        Text(
                                            detalle.cantidadProducto.toString(),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp
                                        )

                                        Button(
                                            onClick = { carritoViewModel.aumentarCantidad(detalle.idProducto) },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4F7A)),
                                            contentPadding = PaddingValues(0.dp),
                                            modifier = Modifier.size(30.dp)
                                        ) { Text("+", color = Color.White) }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            AnimatedVisibility(visible = carrito.productos.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Total: $${carrito.total}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFFDA3B68)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            scope.launch {
                                carritoViewModel.limpiarCarrito() // ✅ limpia memoria y DataStore
                                navController.navigate("InicioCatalogoScreen") {
                                    popUpTo("CarritoScreen") { inclusive = true }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4F7A)),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Finalizar compra", fontSize = 18.sp, color = Color.White)
                    }
                }
            }
        }
    }
}