package com.example.pressbeauty

import com.example.pressbeauty.model.DetalleCarritoUI
import com.example.pressbeauty.model.ProductoUI
import com.example.pressbeauty.viewmodel.CarritoViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class CarritoViewModelTest {

    @Test
    fun testAgregarProducto() = runTest {
        val vm = CarritoViewModel(FakeApplication())

        val producto = ProductoUI(
            idProducto = "10",
            nombreProducto = "Esmalte Rojo",
            descripcionProducto = "bonito",
            precioProducto = 2000,
            stockProducto = 10,
            imagenUrl = ""
        )

        vm.agregarProducto(producto, 2)

        val carrito = vm.carrito.value

        assertEquals(1, carrito.productos.size)
        assertEquals(4000, carrito.total)
        assertEquals("Esmalte Rojo", carrito.productos[0].nombreProducto)
        assertEquals(2, carrito.productos[0].cantidadProducto)
    }

    @Test
    fun testLimpiarCarrito() = runTest {
        val vm = CarritoViewModel(FakeApplication())

        val producto = ProductoUI(
            idProducto = "10",
            nombreProducto = "Esmalte Rojo",
            descripcionProducto = "bonito",
            precioProducto = 2000,
            stockProducto = 10,
            imagenUrl = ""
        )

        vm.agregarProducto(producto, 5)
        vm.limpiarCarrito()

        val carrito = vm.carrito.value

        assertTrue(carrito.productos.isEmpty())
        assertEquals(0, carrito.total)
    }

    @Test
    fun testDisminuirCantidadA1EliminaProducto() = runTest {
        val vm = CarritoViewModel(FakeApplication())

        val producto = ProductoUI(
            idProducto = "10",
            nombreProducto = "Esmalte Rojo",
            descripcionProducto = "bonito",
            precioProducto = 2000,
            stockProducto = 10,
            imagenUrl = ""
        )

        vm.agregarProducto(producto, 1)
        vm.disminuirCantidad("10")

        val carrito = vm.carrito.value

        assertTrue(carrito.productos.isEmpty())
        assertEquals(0, carrito.total)
    }
}
