package com.example.pressbeauty.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFFFFECDC), Color(0xFFFFFAF8))
                        )
                    )
                    .padding(paddingValues)
                    .padding(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    shape = RoundedCornerShape(5.dp),
                    elevation = CardDefaults.cardElevation(5.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDA3B68)),
                        shape = RoundedCornerShape(1.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("<", color = Color.White, fontSize = 20.sp)
                    }
                    AsyncImage(
                        model = producto.imagenUrl,
                        contentDescription = producto.nombreProducto,
                        modifier = Modifier
                            .height(400.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(0.dp)),
                        contentScale = ContentScale.Crop,

                        )
                    Spacer(modifier = Modifier.height(10.dp))


                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = producto.nombreProducto,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        modifier = Modifier.padding(horizontal = 8.dp),

                        color = Color(0xFF802544)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = producto.descripcionProducto,
                        fontSize = 18.sp,
                        color = Color.DarkGray,
                        lineHeight = 24.sp,
                        modifier = Modifier.padding(horizontal = 20.dp),
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Precio: $${producto.precioProducto}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color(0xFF802544),
                            modifier = Modifier.padding(horizontal = 10.dp),

                            )

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { if (cantidadProd > 1) cantidadProd-- },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFFFF4F7A
                                    )
                                ),
                                shape = RoundedCornerShape(5.dp)
                            ) {
                                Text("-", fontSize = 15.sp, color = Color.White)
                            }

                            Spacer(modifier = Modifier.width(15.dp))

                            Text(
                                text = cantidadProd.toString(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.width(15.dp))

                            Button(
                                onClick = { cantidadProd++ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFFFF4F7A
                                    )
                                ),
                                shape = RoundedCornerShape(5.dp)
                            ) {
                                Text("+", fontSize = 15.sp, color = Color.White)
                            }
                        }
                    }
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Button(
                        onClick = {
                            carritoViewModel.agregarProducto(producto, cantidadProd)
                            scope.launch {
                                snackbarHostState.showSnackbar("Producto agregado al carrito")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4F7A)),
                        shape = RoundedCornerShape(100.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Añadir al carrito", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFAF8)),
                contentAlignment = Alignment.Center
            ) {
                Text("Producto no encontrado", color = Color.Gray, fontSize = 18.sp)
            }
        }
    }
}
