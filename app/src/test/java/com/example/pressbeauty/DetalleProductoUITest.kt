package com.example.pressbeauty

import com.example.pressbeauty.model.DetalleProductoUI
import org.junit.Assert.*
import org.junit.Test

class DetalleProductoUITest {

    @Test
    fun crearDetalleProductoCorrectamente() {
        val producto = DetalleProductoUI(
            idProducto = "P1",
            nombreProducto = "Esmalte rojo",
            descripcionProducto = "Color fuerte",
            precioProducto = 3990f
        )

        assertEquals("P1", producto.idProducto)
        assertEquals("Esmalte rojo", producto.nombreProducto)
        assertEquals("Color fuerte", producto.descripcionProducto)
        assertEquals(3990f, producto.precioProducto)
    }
}
