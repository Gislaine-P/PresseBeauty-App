package com.example.pressbeauty

import com.example.pressbeauty.model.*
import org.junit.Test
import org.junit.Assert.*

class EnvioUITest {

    //
    // Pruebas direccion
    //
    @Test
    fun crearDireccionEntrega() {

        val direccion = DireccionEntrega(
            displayName = "Av. Thais donde tai, Viña del Mar",
            lat = -33.33,
            lon = -66.66
        )

        assertEquals("Av. Thais donde tai, Viña del Mar", direccion.displayName)
        assertEquals(-33.33, direccion.lat, 0.0)
        assertEquals(-66.66, direccion.lon, 0.0)
    }

    @Test
    fun direccionEntregaCoordCero() {

        val direccion = DireccionEntrega(
            displayName = "Dirección sin coordenadas",
            lat = 0.0,
            lon = 0.0
        )

        assertEquals("Dirección sin coordenadas", direccion.displayName)
        assertEquals(0.0, direccion.lat, 0.0)
        assertEquals(0.0, direccion.lon, 0.0)
    }



    //
    // Prueba tipo entrega
    //
    @Test
    fun listarTipoEntrega() {
        assertEquals("DOMICILIO", TipoEntrega.DOMICILIO.name)
        assertEquals("RETIRO_LOCAL", TipoEntrega.RETIRO_LOCAL.name)
    }

    @Test
    fun listarIdTipoEntrega() {
        // para asegurarse q sean solo 2 tipos de entrega:
        assertEquals(2, TipoEntrega.values().size)
        // para revisar q los tipo de entrega correctos esten dentro de la lista:
        assertTrue(TipoEntrega.values().contains(TipoEntrega.DOMICILIO))
        assertTrue(TipoEntrega.values().contains(TipoEntrega.RETIRO_LOCAL))
        // para revisar q tengan la id correcta:
        assertEquals(0, TipoEntrega.DOMICILIO.ordinal)
        assertEquals(1, TipoEntrega.RETIRO_LOCAL.ordinal)
    }


    //
    //Pruebas carrito SIN PRODUCTOS
    //

    @Test
    fun carritoSinProdADomicilio() {

        val direccion = DireccionEntrega(
            displayName = "Calle Falsa 123",
            lat = -30.12345,
            lon = -50.12345
        )

        val carrito = CarritoUI(
            idCarrito = "A3214",
            idUsuario = "1233211",
            productos = emptyList(),
            total = 0,
            direccionEntrega = direccion,
            tipoEntrega = TipoEntrega.DOMICILIO
        )

        assertEquals(TipoEntrega.DOMICILIO, carrito.tipoEntrega)
        assertEquals(direccion, carrito.direccionEntrega)
        assertEquals("Calle Falsa 123", carrito.direccionEntrega?.displayName)
    }


    @Test
    fun carritoSinProdRetiroLocal(){

        val carrito = CarritoUI(
            idCarrito = "Z09878",
            idUsuario = "5677654",
            productos = emptyList(),
            total = 0,
            direccionEntrega = null,
            tipoEntrega = TipoEntrega.RETIRO_LOCAL
        )

        assertEquals(TipoEntrega.RETIRO_LOCAL, carrito.tipoEntrega)
        assertNull(carrito.direccionEntrega)

    }


    //
    //Pruebas carrito CON PRODUCTOS
    //

    @Test
    fun carritoConProdDomicilio(){

        val productos = listOf(
            DetalleCarritoUI("1", "1", "img1", "Uñas Halloween", 2, 5000, 10000),
            DetalleCarritoUI("2", "2", "img2", "Uñas Navidad", 1, 3000, 3000),
        )

        val direccion = DireccionEntrega(
            displayName = "Av. Falsa 123",
            lat = -44.444,
            lon = -77.777
        )

        val carrito = CarritoUI(
            idCarrito = "C1",
            idUsuario = "10",
            productos = productos,
            total = 13000,
            direccionEntrega = direccion,
            tipoEntrega = TipoEntrega.DOMICILIO
        )

        assertEquals(13000, carrito.total)
        assertEquals(TipoEntrega.DOMICILIO, carrito.tipoEntrega)
        assertEquals("Av. Falsa 123", carrito.direccionEntrega?.displayName)
        assertEquals(2, carrito.productos.size)
    }


    @Test
    fun carritoConProdRetiroLocal() {

        val productos = listOf(
            DetalleCarritoUI("1", "1", "img1", "Uñas Halloween", 2, 5000, 10000),
            DetalleCarritoUI("2", "2", "img2", "Uñas Navidad", 1, 3000, 3000),
        )

        val direccion = DireccionEntrega(
            displayName = "Av. Falsa 123",
            lat = -44.444,
            lon = -77.777
        )

        val carrito = CarritoUI(
            idCarrito = "C1",
            idUsuario = "10",
            productos = productos,
            total = 13000,
            direccionEntrega = direccion,
            tipoEntrega = TipoEntrega.RETIRO_LOCAL
        )

        assertEquals(13000, carrito.total)
        assertEquals(TipoEntrega.RETIRO_LOCAL, carrito.tipoEntrega)
        assertEquals("Av. Falsa 123", carrito.direccionEntrega?.displayName)
        assertEquals(2, carrito.productos.size)

    }




}