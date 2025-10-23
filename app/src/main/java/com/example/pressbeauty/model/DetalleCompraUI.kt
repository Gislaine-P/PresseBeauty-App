package com.example.pressbeauty.model

data class DetalleCompraUI(
    val idCompra : String,
    val idUsuario : String,
    val productos : List<DetalleCarritoUI>,
    val fechaCompra : String,
    val totalCompra : Float,
    val estadoCompra : String
)
