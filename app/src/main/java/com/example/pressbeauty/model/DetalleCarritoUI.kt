package com.example.pressbeauty.model

data class DetalleCarritoUI(
    val idDetalleCarrito : String,
    val idProducto : String,
    val nombreProducto : String,
    val cantidadProducto : Int,
    val precioUnitario : Int,
    val subtotalCarrito : Int
)
