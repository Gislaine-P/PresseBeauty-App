package com.example.pressbeauty

import com.example.pressbeauty.model.DetalleCarritoUI
import org.junit.Assert.*
import org.junit.Test

class DetalleCarritoUITest {

    @Test
    fun crearDetalleCarrito() {
        val d = DetalleCarritoUI(
            idDetalleCarrito = "D1",
            idProducto = "P10",
            imagenUrl = "http://img.jpg",
            nombreProducto = "Esmalte rojo",
            cantidadProducto = 3,
            precioUnitario = 2000,
            subtotalCarrito = 6000
        )

        assertEquals("D1", d.idDetalleCarrito)
        assertEquals("P10", d.idProducto)
        assertEquals("http://img.jpg", d.imagenUrl)
        assertEquals("Esmalte rojo", d.nombreProducto)
        assertEquals(3, d.cantidadProducto)
        assertEquals(2000, d.precioUnitario)
        assertEquals(6000, d.subtotalCarrito)
    }

    @Test
    fun subtotalCalculado() {
        val cantidad = 4
        val precio = 1500
        val subtotalEsperado = cantidad * precio

        val d = DetalleCarritoUI(
            idDetalleCarrito = "D2",
            idProducto = "P22",
            imagenUrl = "http://img2.jpg",
            nombreProducto = "Kit uñas",
            cantidadProducto = cantidad,
            precioUnitario = precio,
            subtotalCarrito = subtotalEsperado
        )

        assertEquals(subtotalEsperado, d.subtotalCarrito)
    }
}
