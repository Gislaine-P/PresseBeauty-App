package com.example.pressbeauty.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFFFFFDFD)
    ) { paddingValues ->
        if (producto != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFFFFF5F3), Color.White)
                        )
                    )
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopStart
                ) {
                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4B4B4)),
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(42.dp)
                    ) {
                        Text("<", fontSize = 20.sp, color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                AsyncImage(
                    model = producto.imagenUrl,
                    contentDescription = producto.nombreProducto,
                    modifier = Modifier
                        .height(350.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    producto.nombreProducto,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color(0xFFB06F6F)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    producto.descripcionProducto,
                    color = Color.DarkGray,
                    lineHeight = 22.sp,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "Precio: $${producto.precioProducto}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = Color(0xFFB06F6F)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { if (cantidadProd > 1) cantidadProd-- },
                        border = ButtonDefaults.outlinedButtonBorder,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFB06F6F))
                    ) { Text("-") }

                    Text(
                        "$cantidadProd",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    OutlinedButton(
                        onClick = { cantidadProd++ },
                        border = ButtonDefaults.outlinedButtonBorder,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFB06F6F))
                    ) { Text("+") }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        carritoViewModel.agregarProducto(producto, cantidadProd)
                        scope.launch { snackbarHostState.showSnackbar("Producto agregado al carrito") }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4B4B4)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Añadir al carrito", color = Color.White, fontSize = 17.sp)
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text("Producto no encontrado", color = Color.Gray, fontSize = 18.sp)
            }
        }
    }
}
