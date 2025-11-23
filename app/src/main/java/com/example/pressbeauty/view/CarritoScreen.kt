package com.example.pressbeauty.view

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pressbeauty.model.Nominatim
import com.example.pressbeauty.remote.RetrofitInstance
import com.example.pressbeauty.view.components.NavInferior
import com.example.pressbeauty.viewmodel.CarritoViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CarritoScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel
) {
    val carrito by carritoViewModel.carrito.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

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
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                contentColor = Color(0xFFB06F6F)
                                            ),
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
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                contentColor = Color(0xFFB06F6F)
                                            ),
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

            //CODIGO ANTIGUO
            /* AnimatedVisibility(visible = carrito.productos.isNotEmpty()) {
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
            }*/

            //FUNCIONALIDAD PARA BOTONES Y LLAMADO DE API
            AnimatedVisibility(visible = carrito.productos.isNotEmpty()) {
                //variables para guardar direccion y el tipo de entrega que se realzara
                var direccion by remember { mutableStateOf("") }
                var tipoEntrega by remember { mutableStateOf("") }

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

                    //texto para poner la direccion
                    TextField(
                        value = direccion,
                        onValueChange = { direccion = it },
                        label = { Text("Dirección") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    //boton para elegir si quiere a domicilio o retiro
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { tipoEntrega = "retiro" }, modifier = Modifier.weight(1f)) {
                            Text("Retiro en tienda")
                        }
                        Button(onClick = { tipoEntrega = "domicilio" }, modifier = Modifier.weight(1f)) {
                            Text("Envío a domicilio")
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    //boton de compra con logica parcial
                    Button(
                        onClick = {
                            scope.launch {

                                //si el pone opcion retiro el carro automaticamente se limpia
                                if (tipoEntrega == "retiro") {
                                    snackbarHostState.showSnackbar("Compra con retiro en tienda")
                                    carritoViewModel.limpiarCarrito()
                                    navController.navigate("InicioCatalogoScreen") {
                                        popUpTo("CarritoScreen") { inclusive = true }
                                    }
                                    //si pone a domicilio se llamara la API de Nominatim
                                } else if (tipoEntrega == "domicilio" && direccion.isNotBlank()) {
                                    // Aquí se llama a la API de Nominatim
                                    val call = RetrofitInstance.api.searchLocation(direccion)
                                    call.enqueue(object : Callback<List<Nominatim>> {
                                        override fun onResponse(
                                            call: Call<List<Nominatim>>,
                                            response: Response<List<Nominatim>>
                                        ) {
                                            if (response.isSuccessful && response.body()?.isNotEmpty() == true) {
                                                val location = response.body()!![0]



                                                //prueba temporal para mostrar resultado en Toast y validar que la API responde (ELIMINAR O COMENTAR SI SE NECESITA)
                                                val mensaje = "Dirección encontrada: ${location.display_name}\nLat: ${location.lat}, Lon: ${location.lon}"
                                                Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()


                                            //aca se debe continuar la logica real (limpiar carro, navegar, guardar lat/lon)


                                            } else {
                                                //Si no selecciona retiro o a domicilio / si no escribe direccion
                                                Toast.makeText(context, "No se encontró la dirección", Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                        override fun onFailure(call: Call<List<Nominatim>>, t: Throwable) {
                                            Toast.makeText(context, "Error al conectar con Nominatim", Toast.LENGTH_SHORT).show()
                                        }
                                    })
                                } else {
                                    snackbarHostState.showSnackbar("Selecciona tipo de entrega y escribe la dirección")
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
