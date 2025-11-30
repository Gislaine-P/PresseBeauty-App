package com.example.pressbeauty

import com.example.pressbeauty.model.*
import org.junit.Test
import org.junit.Assert.*

class CarritoUITest {

    @Test
    fun crearCarritoCorrectamente() {
        val carrito = CarritoUI(
            idCarrito = "C1",
            idUsuario = "10",
            productos = emptyList(),
            total = 0,
            direccionEntrega = null,
            tipoEntrega = null
        )

        assertEquals("C1", carrito.idCarrito)
        assertEquals("10", carrito.idUsuario)
        assertTrue(carrito.productos.isEmpty())
        assertEquals(0, carrito.total)
        assertNull(carrito.direccionEntrega)
        assertNull(carrito.tipoEntrega)
    }

    @Test
    fun carritoConProductosCalculaTotal() {
        val productos = listOf(
            DetalleCarritoUI("1","P1","img","Producto 1",2,1000,2000),
            DetalleCarritoUI("2","P2","img","Producto 2",1,3000,3000),
        )

        val carrito = CarritoUI(
            idCarrito = "C1",
            idUsuario = "10",
            productos = productos,
            total = productos.sumOf { it.subtotalCarrito }
        )

        assertEquals(5000, carrito.total)
    }
}
