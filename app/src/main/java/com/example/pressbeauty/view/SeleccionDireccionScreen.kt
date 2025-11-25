package com.example.pressbeauty.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.pressbeauty.model.DireccionEntrega
import com.example.pressbeauty.model.TipoEntrega
import com.example.pressbeauty.viewmodel.CarritoViewModel

@Composable
fun SeleccionDireccionScreen(
    onDireccionSeleccionada: (DireccionEntrega) -> Unit,
    onCancelar: () -> Unit,
    carritoViewModel: CarritoViewModel
) {
    var busqueda by remember { mutableStateOf("") }
    var tipoEntregaSeleccionado by remember { mutableStateOf<TipoEntrega?>(null) }
    val direccionesEncontradas by carritoViewModel.direccionesEncontradas.collectAsState()
    val cargando by carritoViewModel.cargandoDireccion.collectAsState()
    val error by carritoViewModel.errorDireccion.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Selecciona método de entrega",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // elegir tipo de entrega
        Text(
            text = "¿Cómo quieres recibir tu pedido?",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RadioButton(
                selected = tipoEntregaSeleccionado == TipoEntrega.RETIRO_LOCAL,
                onClick = {
                    tipoEntregaSeleccionado = TipoEntrega.RETIRO_LOCAL
                    // limpiar busqueda al seleccionar retiro
                    busqueda = ""
                    carritoViewModel.limpiarBusquedaDirecciones()
                }
            )
            Text(
                text = "Retiro en local",
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = tipoEntregaSeleccionado == TipoEntrega.DOMICILIO,
                onClick = {
                    tipoEntregaSeleccionado = TipoEntrega.DOMICILIO
                }
            )
            Text(
                text = "Envío a domicilio",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (tipoEntregaSeleccionado == TipoEntrega.DOMICILIO) {
            // busuqeda direccion domicilio
            Text(
                text = "Ingresa tu dirección para el envío:",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = busqueda,
                onValueChange = {
                    busqueda = it
                    // buscar direcciones mientras se escribe la misma
                    if (it.length >= 3) {
                        carritoViewModel.buscarDirecciones(it)
                    } else {
                        carritoViewModel.limpiarBusquedaDirecciones()
                    }
                },
                label = { Text("Ingresa tu dirección") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ej: Av. Siempre Viva 123, Springfield") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // estados carga y error
            when {
                cargando -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                error != null -> {
                    Text(
                        text = error!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                direccionesEncontradas.isNotEmpty() -> {
                    Text(
                        text = "Selecciona una dirección:",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(direccionesEncontradas) { direccion ->
                            Card(
                                onClick = {
                                    onDireccionSeleccionada(direccion)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.LocationOn,
                                        contentDescription = "Dirección",
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(
                                        text = direccion.displayName,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
                busqueda.length >= 3 -> {
                    Text(
                        text = "No se encontraron direcciones",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        } else if (tipoEntregaSeleccionado == TipoEntrega.RETIRO_LOCAL) {


            //
            // Informacion para retirar en el local

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Retiro en local",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Puedes retirar tu pedido en nuestro local ubicado en:",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Av. Calle 123, Viña del Mar",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Horario: Lunes a Viernes 9:00 - 18:00",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botones de accion
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onCancelar,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Text("Cancelar")
            }

            Button(
                onClick = {
                    when (tipoEntregaSeleccionado) {
                        TipoEntrega.RETIRO_LOCAL -> {
                            onDireccionSeleccionada(
                                DireccionEntrega(
                                    displayName = "Retiro en local - Av. Calle 123",
                                    lat = 0.0,
                                    lon = 0.0,
                                )
                            )
                        }
                        TipoEntrega.DOMICILIO -> {
                            if (direccionesEncontradas.isNotEmpty()) {

                                onDireccionSeleccionada(direccionesEncontradas.first())

                            } else {

                                android.widget.Toast.makeText(
                                    context,
                                    "Por favor selecciona una dirección de la lista",
                                    android.widget.Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        null -> {
                            android.widget.Toast.makeText(
                                context,
                                "Por favor selecciona un método de entrega",
                                android.widget.Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                enabled = tipoEntregaSeleccionado != null &&
                        (tipoEntregaSeleccionado == TipoEntrega.RETIRO_LOCAL ||
                                (tipoEntregaSeleccionado == TipoEntrega.DOMICILIO && direccionesEncontradas.isNotEmpty()))
            ) {
                Text("Confirmar")
            }
        }
    }
}