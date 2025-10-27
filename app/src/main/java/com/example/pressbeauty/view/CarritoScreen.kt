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
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = { NavInferior(navController) },
        containerColor = Color(0xFFFDFBFA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFF7F5), Color.White)
                    )
                )
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            Text(
                text = "Tu Carrito",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB06F6F),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 12.dp)
            )

            AnimatedVisibility(visible = carrito.productos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Tu carrito está vacío",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color(0xFFB9AFAF),
                            fontSize = 17.sp
                        )
                    )
                }
            }

            AnimatedVisibility(visible = carrito.productos.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(carrito.productos) { detalle ->
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = detalle.imagenUrl,
                                    contentDescription = detalle.nombreProducto,
                                    modifier = Modifier
                                        .size(90.dp)
                                        .clip(RoundedCornerShape(16.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        detalle.nombreProducto,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp,
                                        color = Color(0xFF4B4B4B)
                                    )
                                    Text(
                                        "Precio: $${detalle.precioUnitario}",
                                        color = Color(0xFF9C9C9C),
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        "Subtotal: $${detalle.subtotalCarrito}",
                                        color = Color(0xFFBF7C7C),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        OutlinedButton(
                                            onClick = { carritoViewModel.disminuirCantidad(detalle.idProducto) },
                                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFB06F6F)),
                                            modifier = Modifier.size(28.dp),
                                            contentPadding = PaddingValues(0.dp)
                                        ) { Text("-") }

                                        Text(
                                            detalle.cantidadProducto.toString(),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )

                                        OutlinedButton(
                                            onClick = { carritoViewModel.aumentarCantidad(detalle.idProducto) },
                                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFB06F6F)),
                                            modifier = Modifier.size(28.dp),
                                            contentPadding = PaddingValues(0.dp)
                                        ) { Text("+") }
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
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFFFF4F2))
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Total: $${carrito.total}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFFB06F6F)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                val exito = (0..100).random() < 70
                                if (exito) {
                                    carritoViewModel.limpiarCarrito()
                                    snackbarHostState.showSnackbar("Compra realizada con éxito!!")
                                    navController.navigate("InicioCatalogoScreen") {
                                        popUpTo("CarritoScreen") { inclusive = true }
                                    }
                                } else {
                                    snackbarHostState.showSnackbar("La compra no pudo completarse")
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4B4B4)),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Realizar compra", fontSize = 17.sp, color = Color.White)
                    }
                }
            }
        }
    }
}
