package com.example.pressbeauty.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pressbeauty.model.CarritoUI
import com.example.pressbeauty.model.DetalleCarritoUI
import com.example.pressbeauty.model.ProductoUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class CarritoViewModel : ViewModel() {

    // Estado del carrito
    private val _carrito = MutableStateFlow(
        CarritoUI(
            idCarrito = UUID.randomUUID().toString(),
            idUsuario = "1", // luego puedes reemplazar por el usuario real desde DataStore
            productos = emptyList(),
            total = 0
        )
    )
    val carrito: StateFlow<CarritoUI> = _carrito

    /** ✅ Agregar producto al carrito */
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
    }


    /** 🗑️ Eliminar producto del carrito */
    fun eliminarProducto(idProducto: String) {
        _carrito.update { carritoActual ->
            val listaActual = carritoActual.productos.filterNot { it.idProducto == idProducto }
            carritoActual.copy(
                productos = listaActual,
                total = listaActual.sumOf { it.subtotalCarrito }
            )
        }
    }

    /** ➕ Aumentar cantidad */
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
    }

    /** ➖ Disminuir cantidad */
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
    }

    /** 🧹 Vaciar carrito */
    fun limpiarCarrito() {
        _carrito.value = _carrito.value.copy(productos = emptyList(), total = 0)
    }

}
