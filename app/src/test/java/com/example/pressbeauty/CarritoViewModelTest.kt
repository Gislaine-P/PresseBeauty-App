package com.example.pressbeauty

import com.example.pressbeauty.model.DetalleCarritoUI
import com.example.pressbeauty.model.ProductoUI
import com.example.pressbeauty.viewmodel.CarritoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CarritoViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

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

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
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
