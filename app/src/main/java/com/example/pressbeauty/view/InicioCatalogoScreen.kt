package com.example.pressbeauty.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pressbeauty.R
import com.example.pressbeauty.view.components.NavInferior
import com.example.pressbeauty.viewmodel.CarritoViewModel
import com.example.pressbeauty.viewmodel.ProductoViewModel
import com.example.pressbeauty.viewmodel.UsuarioViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InicioCatalogoScreen(
    navController: NavController,
    productoViewModel: ProductoViewModel,
    usuarioViewModel: UsuarioViewModel,
    carritoViewModel: CarritoViewModel
) {
    val productos by productoViewModel.productos.collectAsState()
    val usuario by usuarioViewModel.estado.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = { NavInferior(navController) },
        containerColor = Color(0xFFFFFDFD)
    ) { paddingValues ->

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFFE5A6A6), strokeWidth = 5.dp)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(colors = listOf(Color(0xFFFFF5F3), Color.White)))
                    .padding(paddingValues)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {

                Text(
                    text = "Bienvenida, ${usuario.nombre}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color(0xFFB06F6F),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )

                LazyColumn {
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.banner_catalogo),
                            contentDescription = "Banner",
                            modifier = Modifier.fillMaxWidth().height(180.dp)
                                .clip(RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize().padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(productos) { producto ->
                        Card(
                            modifier = Modifier.fillMaxWidth().height(250.dp)
                                .clickable {
                                    isLoading = true
                                    scope.launch {
                                        delay(700)
                                        isLoading = false
                                        navController.navigate("productoScreen/${producto.idProducto}")
                                    }
                                },
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(18.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = producto.imagenUrl,
                                    contentDescription = producto.nombreProducto,
                                    modifier = Modifier.height(150.dp).fillMaxWidth()
                                        .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = producto.nombreProducto,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    maxLines = 1,
                                    color = Color(0xFF3C3C3C),
                                    modifier = Modifier.padding(horizontal = 6.dp)
                                )

                                Text(
                                    text = "$${producto.precioProducto}",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    color = Color(0xFF9A6A6A)
                                )

                                Spacer(modifier = Modifier.height(6.dp))
                                Button(
                                    onClick = { navController.navigate("ProductoScreen/${producto.idProducto}") },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4B4B4)),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("Añadir al carrito", color = Color.White, fontSize = 15.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
