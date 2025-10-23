package com.example.pressbeauty.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pressbeauty.R
import com.example.pressbeauty.viewmodel.ProductoViewModel
import com.example.pressbeauty.viewmodel.UsuarioViewModel



@Composable
fun InicioCatalogoScreen(navController: NavController,
                         productoViewModel: ProductoViewModel,
                         usuarioViewModel: UsuarioViewModel) {

    val productos by productoViewModel.productos.collectAsState()
    val usuario by usuarioViewModel.estado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        LazyColumn {
            item {
                Image(
                    painter = painterResource(id = R.drawable.banner),
                    contentDescription = "Banner",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }


            // 👋 Texto de bienvenida
            Text(
                text = "Bienvenido, ${usuario.nombre.ifBlank { "Usuario" }}",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )


            IconButton(
                onClick = { navController.navigate("CarritoScreen") }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.rounded_shopping_cart_24),
                    contentDescription = "Carrito de compras"
                )
            }

            IconButton(
                onClick = { navController.navigate("PerfilUsuarioScreen") }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_person_24),
                    contentDescription = "Perfil"
                )
            }

            // Rejilla con 2 columnas
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(productos) { producto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clickable {
                                navController.navigate("productoScreen/${producto.idProducto}")
                            },
                        elevation = CardDefaults.cardElevation(6.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Imagen del producto (desde URL)
                            AsyncImage(
                                model = producto.imagenUrl,
                                contentDescription = producto.nombreProducto,
                                modifier = Modifier
                                    .height(170.dp)
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Nombre del producto
                            Text(
                                text = producto.nombreProducto,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                maxLines = 1,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )

                            // Precio del producto
                            Text(
                                text = "$${producto.precioProducto}",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }


