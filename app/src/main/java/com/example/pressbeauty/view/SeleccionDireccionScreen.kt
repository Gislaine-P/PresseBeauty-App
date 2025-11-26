package com.example.pressbeauty.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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

    // limpiar busqueda cuando cambia el tipo de entrega
    LaunchedEffect(tipoEntregaSeleccionado) {
        if (tipoEntregaSeleccionado == TipoEntrega.RETIRO_LOCAL) {
            busqueda = ""
            carritoViewModel.limpiarBusquedaDirecciones()
        }
    }

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
                    Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Buscando direcciones...")
                    }
                }
                error != null -> {
                    Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = "Error",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = error!!,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                direccionesEncontradas.isNotEmpty() -> {

                    Text(
                        text = "${direccionesEncontradas.size} dirección(es) encontrada(s):",
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
                                    .padding(4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.LocationOn,
                                        contentDescription = "Dirección",
                                        modifier = Modifier.padding(end = 12.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = direccion.displayName,
                                            style = MaterialTheme.typography.bodyMedium,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = "Coordenadas: ${"%.4f".format(direccion.lat)}, ${"%.4f".format(direccion.lon)}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                busqueda.length >= 3 -> {
                    Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Icon(
                            Icons.Default.SearchOff,
                            contentDescription = "No encontrado",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "No se encontraron direcciones para:\n\"$busqueda\"",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
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