package com.example.pressbeauty.model

data class CarritoUI(
    val idCarrito : String,
    val idUsuario : String,
    val productos: List<DetalleCarritoUI>,
    val total : Float
)
