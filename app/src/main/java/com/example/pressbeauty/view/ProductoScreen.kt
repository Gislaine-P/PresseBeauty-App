package com.example.pressbeauty.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        if (producto != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFEC))
                    .padding(paddingValues)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AsyncImage(
                    model = producto.imagenUrl,
                    contentDescription = producto.nombreProducto,
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp)),

                )
                Text(
                    text = producto.nombreProducto,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color(0xFFFF4F7A)
                )

                Text(
                    text = producto.descripcionProducto,
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

                Text(
                    text = "Precio: $${producto.precioProducto}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFFDA3B68)
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { if (cantidadProd > 1) cantidadProd-- },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4F7A))
                    ) {
                        Text("-", fontSize = 20.sp, color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = cantidadProd.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = { cantidadProd++ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4F7A))
                    ) {
                        Text("+", fontSize = 20.sp, color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(90.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(100.dp)
                ) {
                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDA3B68))
                    ) {
                        Text("Volver", color = Color.White)
                    }

                    Button(
                        onClick = {
                            carritoViewModel.agregarProducto(producto, cantidadProd)
                            scope.launch {
                                snackbarHostState.showSnackbar("Producto agregado al carrito")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4F7A))
                    ) {
                        Text("Agregar al carrito", color = Color.White)
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Producto no encontrado", color = Color.Gray)
            }
        }
    }
}
