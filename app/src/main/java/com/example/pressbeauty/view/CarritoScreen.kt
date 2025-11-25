package com.example.pressbeauty.view


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import com.example.pressbeauty.model.TipoEntrega
import com.example.pressbeauty.remote.RetrofitInstance
import com.example.pressbeauty.view.components.NavInferior
import com.example.pressbeauty.view.components.SeleccionDireccionScreen
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

    // estado para mostrar la pantalla de direccion de domicilio
    var mostrarSeleccionDireccion by remember { mutableStateOf(false) }

    //forzar seleccion de tipo de entrega
    if (carrito.tipoEntrega == null){
        LaunchedEffect(Unit) {mostrarSeleccionDireccion = true }
    }

    // para mostrar pantalla seleccion de direccion cuando sea necesario:
    if (mostrarSeleccionDireccion) {
        SeleccionDireccionScreen(
            onDireccionSeleccionada = { direccion ->
                // para determinar si es retiro local o envio a domicilio
                if (direccion.displayName.contains("Retiro en local")) {
                    carritoViewModel.setTipoEntrega(TipoEntrega.RETIRO_LOCAL)
                } else {
                    carritoViewModel.setDireccionEntrega(direccion)
                }
                mostrarSeleccionDireccion = false
            },
            onCancelar = {
                if (carrito.tipoEntrega != null) {
                    mostrarSeleccionDireccion = false
                }
            },
            carritoViewModel = carritoViewModel
        )
        return
    }



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

                //variables para guardar direccion y el tipo de entrega
                var direccion by remember { mutableStateOf("") }
                var tipoEntrega by remember { mutableStateOf("") }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFFFF4F2))
                        .padding(20.dp)
                ) {
                    // info tipo de entrega actual
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8E8E8))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Método de entrega:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFFB06F6F)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            when (carrito.tipoEntrega) {
                                TipoEntrega.RETIRO_LOCAL -> {
                                    Text(
                                        "Retiro en local",
                                        fontSize = 14.sp,
                                        color = Color(0xFF4B4B4B)
                                    )
                                    Text(
                                        "Puedes retirar tu pedido en nuestro local",
                                        fontSize = 12.sp,
                                        color = Color(0xFF9C9C9C)
                                    )
                                }
                                TipoEntrega.DOMICILIO -> {
                                    carrito.direccionEntrega?.let { direccion ->
                                        Text(
                                            "Envío a domicilio",
                                            fontSize = 14.sp,
                                            color = Color(0xFF4B4B4B)
                                        )
                                        Text(
                                            direccion.displayName,
                                            fontSize = 12.sp,
                                            color = Color(0xFF9C9C9C)
                                        )
                                    } ?: run {
                                        Text(
                                            "Envío a domicilio - Dirección no seleccionada",
                                            fontSize = 14.sp,
                                            color = Color(0xFFBF7C7C)
                                        )
                                    }
                                }
                                null -> {
                                    Text(
                                        "No se ha seleccionado un método de entrega",
                                        fontSize = 14.sp,
                                        color = Color(0xFFBF7C7C)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = { mostrarSeleccionDireccion = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFE8D0D0)
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    Icons.Default.LocationOn,
                                    contentDescription = "Ubicación",
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Cambiar método de entrega", fontSize = 14.sp)
                            }
                        }
                    }

                    Text(
                        text = "Total: $${carrito.total}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFFB06F6F)
                    )



                    Spacer(modifier = Modifier.height(10.dp))

                    //boton de compra
                    Button(
                        onClick = {
                            scope.launch {
                                when (carrito.tipoEntrega) {
                                    TipoEntrega.RETIRO_LOCAL -> {
                                        // Lógica para retiro local
                                        val exito = (0..100).random() < 70
                                        if (exito) {
                                            carritoViewModel.limpiarCarrito()
                                            snackbarHostState.showSnackbar("¡Compra con retiro en local realizada con éxito!")
                                            navController.navigate("InicioCatalogoScreen") {
                                                popUpTo("CarritoScreen") { inclusive = true }
                                            }
                                        } else {
                                            snackbarHostState.showSnackbar("La compra no pudo completarse")
                                        }
                                    }
                                    TipoEntrega.DOMICILIO -> {
                                        if (carrito.direccionEntrega != null) {
                                            //
                                            // LOGICA ENVIO A DOMICILIO
                                            //
                                            val exito = (0..100).random() < 70
                                            if (exito) {
                                                carritoViewModel.limpiarCarrito()
                                                snackbarHostState.showSnackbar("¡Compra con envío a domicilio realizada con éxito!")
                                                navController.navigate("InicioCatalogoScreen") {
                                                    popUpTo("CarritoScreen") { inclusive = true }
                                                }
                                            } else {
                                                snackbarHostState.showSnackbar("La compra no pudo completarse")
                                            }
                                        } else {
                                            snackbarHostState.showSnackbar("Por favor selecciona una dirección para el envío a domicilio")
                                        }
                                    }
                                    null -> {
                                        snackbarHostState.showSnackbar("Por favor selecciona un método de entrega")
                                    }
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
}}
