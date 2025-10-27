package com.example.pressbeauty.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pressbeauty.datastore.CarritoDataStore
import com.example.pressbeauty.model.CarritoUI
import com.example.pressbeauty.model.DetalleCarritoUI
import com.example.pressbeauty.model.ProductoUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class CarritoViewModel(application: Application): AndroidViewModel(application) {

    private val dataStore = CarritoDataStore(application)

    private val _carrito = MutableStateFlow(
        CarritoUI(
            idCarrito = UUID.randomUUID().toString(),
            idUsuario = "1",
            productos = emptyList(),
            total = 0
        )
    )
    val carrito: StateFlow<CarritoUI> = _carrito


    init {
        viewModelScope.launch {
            val carritoGuardado = dataStore.obtenerCarrito().first()
            if(carritoGuardado.productos.isNotEmpty()){
                _carrito.value = carritoGuardado
            }
        }
    }

    private fun guardarEstado(){
        viewModelScope.launch {
            dataStore.guardarCarrito(_carrito.value)
        }
    }

    private fun vaciarCarro(){
        viewModelScope.launch {
            dataStore.limpiarCarrito()
        }
    }



    fun agregarProducto(producto: ProductoUI, cantidadProd : Int) {
        _carrito.update { carritoActual ->
            val listaActual = carritoActual.productos.toMutableList()
            val existente = listaActual.find { it.idProducto == producto.idProducto }

            if (existente != null) {
                val nuevaCantidad = existente.cantidadProducto + cantidadProd
                val actualizado = existente.copy(
                    cantidadProducto = nuevaCantidad,
                    subtotalCarrito = nuevaCantidad * existente.precioUnitario
                )
                listaActual[listaActual.indexOf(existente)] = actualizado
            } else {
                val nuevo = DetalleCarritoUI(
                    idDetalleCarrito = UUID.randomUUID().toString(),
                    idProducto = producto.idProducto,
                    imagenUrl = producto.imagenUrl,
                    nombreProducto = producto.nombreProducto,
                    cantidadProducto = cantidadProd,
                    precioUnitario = producto.precioProducto,
                    subtotalCarrito = producto.precioProducto * cantidadProd
                )
                listaActual.add(nuevo)
            }

            carritoActual.copy(
                productos = listaActual,
                total = listaActual.sumOf { it.subtotalCarrito}
            )
        }
        guardarEstado()
    }



    fun eliminarProducto(idProducto: String) {
        _carrito.update { carritoActual ->
            val listaActual = carritoActual.productos.filterNot { it.idProducto == idProducto }
            carritoActual.copy(
                productos = listaActual,
                total = listaActual.sumOf { it.subtotalCarrito }
            )
        }
        guardarEstado()
    }


    fun aumentarCantidad(idProducto: String) {
        _carrito.update { carritoActual ->
            val listaActual = carritoActual.productos.map {
                if (it.idProducto == idProducto) {
                    val nuevaCantidad = it.cantidadProducto + 1
                    it.copy(
                        cantidadProducto = nuevaCantidad,
                        subtotalCarrito = nuevaCantidad * it.precioUnitario
                    )
                } else it
            }
            carritoActual.copy(
                productos = listaActual,
                total = listaActual.sumOf { it.subtotalCarrito }
            )
        }
        guardarEstado()
    }


    fun disminuirCantidad(idProducto: String) {
        _carrito.update { carritoActual ->
            val listaActual = carritoActual.productos.mapNotNull {
                if (it.idProducto == idProducto) {
                    val nuevaCantidad = it.cantidadProducto - 1
                    if (nuevaCantidad > 0)
                        it.copy(
                            cantidadProducto = nuevaCantidad,
                            subtotalCarrito = nuevaCantidad * it.precioUnitario
                        )
                    else null
                } else it
            }
            carritoActual.copy(
                productos = listaActual,
                total = listaActual.sumOf { it.subtotalCarrito }
            )
        }
        guardarEstado()
    }

    fun limpiarCarrito() {
        _carrito.value = _carrito.value.copy(productos = emptyList(), total = 0)
        vaciarCarro()
    }

}
