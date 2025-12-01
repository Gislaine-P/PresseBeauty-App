package com.example.pressbeauty

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import com.example.pressbeauty.model.ProductoUI
import com.example.pressbeauty.viewmodel.CarritoViewModel
import java.util.UUID

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class CarritoViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var vm: CarritoViewModel

    @Before
    fun setup() = runTest(testDispatcher) {
        Dispatchers.setMain(testDispatcher)
        val context = ApplicationProvider.getApplicationContext<Application>()

        vm = CarritoViewModel(context)

        vm.limpiarCarrito()
        advanceUntilIdle()
    }

    @After
    fun tearDown() {
        testDispatcher.scheduler.advanceUntilIdle()
        Dispatchers.resetMain()
    }

    @Test
    fun testAgregarProducto() = runTest {
        val productoId = UUID.randomUUID().toString()

        val producto = ProductoUI(
            idProducto = productoId,
            nombreProducto = "Esmalte Rojo",
            descripcionProducto = "bonito",
            precioProducto = 2000,
            stockProducto = 10,
            imagenUrl = ""
        )


        vm.limpiarCarrito()
        advanceUntilIdle()
        assertEquals(0, vm.carrito.value.total)

        vm.agregarProducto(producto, 2)
        advanceUntilIdle()

        val carrito = vm.carrito.value

        assertEquals(1, carrito.productos.size)
        assertEquals(4000, carrito.total)
    }


    @Test
    fun testLimpiarCarrito() = runTest {
        val producto = ProductoUI(
            idProducto = UUID.randomUUID().toString(),
            nombreProducto = "Esmalte Rojo",
            descripcionProducto = "bonito",
            precioProducto = 2000,
            stockProducto = 10,
            imagenUrl = ""
        )

        vm.limpiarCarrito()
        advanceUntilIdle()

        vm.agregarProducto(producto, 5)
        advanceUntilIdle()

        vm.limpiarCarrito()
        advanceUntilIdle()

        val carrito = vm.carrito.value

        assertTrue(carrito.productos.isEmpty())
        assertEquals(0, carrito.total)
    }


    @Test
    fun testDisminuirCantidadA1EliminaProducto() = runTest {
        val productoId = UUID.randomUUID().toString()

        val producto = ProductoUI(
            idProducto = productoId,
            nombreProducto = "Esmalte Rojo",
            descripcionProducto = "bonito",
            precioProducto = 2000,
            stockProducto = 10,
            imagenUrl = ""
        )

        vm.limpiarCarrito()
        advanceUntilIdle()

        vm.agregarProducto(producto, 1)
        advanceUntilIdle()

        vm.disminuirCantidad(productoId)
        advanceUntilIdle()

        val carrito = vm.carrito.value

        assertTrue(carrito.productos.isEmpty())
        assertEquals(0, carrito.total)
    }

    @Test
    fun testAumentarCantidad() = runTest {
        val productoId = UUID.randomUUID().toString()
        val precio = 5000
        val producto = ProductoUI(
            idProducto = productoId,
            nombreProducto = "Lápiz Labial",
            descripcionProducto = "Test",
            precioProducto = precio,
            stockProducto = 10,
            imagenUrl = ""
        )

        vm.agregarProducto(producto, 2)
        advanceUntilIdle()

        assertEquals(2, vm.carrito.value.productos.first { it.idProducto == productoId }.cantidadProducto)
        assertEquals(12000, vm.carrito.value.total)

        vm.aumentarCantidad(productoId)
        advanceUntilIdle()

        val carrito = vm.carrito.value
        val productoEnCarrito = carrito.productos.first { it.idProducto == productoId }

        assertEquals(3, productoEnCarrito.cantidadProducto)
        assertEquals(18000, productoEnCarrito.subtotalCarrito)
        assertEquals(18000, carrito.total)
    }
}