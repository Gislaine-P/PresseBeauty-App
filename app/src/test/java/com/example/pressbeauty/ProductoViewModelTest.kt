package com.example.pressbeauty

import com.example.pressbeauty.viewmodel.ProductoViewModel
import org.junit.Assert.*
import org.junit.Test

class ProductoViewModelTest {

    private val vm = ProductoViewModel()

    @Test
    fun productosNoNulos() {
        assertNotNull(vm.productos.value)
    }

    @Test
    fun listaAlmacenaProductos() {
        assertEquals(8, vm.productos.value.size)
    }

    @Test
    fun productoNombreValido() {
        assertEquals("Celestial Butterfly", vm.productos.value[0].nombreProducto)
    }

    @Test
    fun productoPrecioValido() {
        assertEquals(17000, vm.productos.value[0].precioProducto)
    }

    @Test
    fun imagenProductoNoVacia() {
        vm.productos.value.forEach {
            assertTrue(it.imagenUrl.isNotBlank())
        }
    }

    @Test
    fun precioValidoMayorCero() {
        vm.productos.value.forEach {
            assertTrue(it.precioProducto > 0)
        }
    }

    @Test
    fun stockValidoMayorCero() {
        vm.productos.value.forEach {
            assertTrue(it.stockProducto > 0)
        }
    }
}
