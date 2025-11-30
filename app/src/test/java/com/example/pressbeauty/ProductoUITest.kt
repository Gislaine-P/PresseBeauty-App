package com.example.pressbeauty

import com.example.pressbeauty.model.ProductoUI
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductoUITest {

    @Test
    fun testProductoCreacion() {
        val producto = ProductoUI(
            idProducto = "99",
            imagenUrl = "http://imagen.com",
            nombreProducto = "Gel UV",
            descripcionProducto = "super brillo",
            stockProducto = 12,
            precioProducto = 5990
        )

        assertEquals("99", producto.idProducto)
        assertEquals("Gel UV", producto.nombreProducto)
        assertEquals(12, producto.stockProducto)
        assertEquals(5990, producto.precioProducto)
    }

    @Test
    fun testProductoEquals() {
        val p1 = ProductoUI("1", "", "Esmalte", "desc", 10, 2000)
        val p2 = ProductoUI("1", "", "Esmalte", "desc", 10, 2000)

        assertEquals(p1, p2)
    }
}
