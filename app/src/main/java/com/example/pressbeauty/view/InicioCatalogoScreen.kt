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
import androidx.compose.material3.ButtonDefaults
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
    carritoViewModel : CarritoViewModel
) {
    val productos by productoViewModel.productos.collectAsState()
    val usuario by usuarioViewModel.estado.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = { NavInferior(navController) }
    ) { paddingValues ->

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFFFF4F7A),
                    strokeWidth = 5.dp
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFFFFECDC), Color(0xFFFFFAF8))
                        )
                    )
                    .padding(paddingValues)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {

                Text(
                    text = "Bienvenida, ${usuario.nombre } ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color(0xFFFF4F7A),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )

                LazyColumn {
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.banner_catalogo),
                            contentDescription = "Banner",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(productos) { producto ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .clickable {
                                    isLoading = true
                                    scope.launch {
                                        delay(800)
                                        isLoading = false
                                        navController.navigate("productoScreen/${producto.idProducto}")
                                    }
                                },
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = producto.imagenUrl,
                                    contentDescription = producto.nombreProducto,
                                    modifier = Modifier
                                        .height(160.dp)
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = producto.nombreProducto,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    maxLines = 1,
                                    color = Color(0xFF333333),
                                    modifier = Modifier.padding(horizontal = 6.dp)
                                )

                                Text(
                                    text = "$${producto.precioProducto}",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Button(
                                    onClick = { navController.navigate("ProductoScreen/${producto.idProducto}") } ,
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4F7A)),


                                    ) {
                                    Text("Añadir al carrito", color = Color.White, fontSize = 16.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
