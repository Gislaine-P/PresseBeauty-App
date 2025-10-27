package com.example.pressbeauty.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "compras")
data class Compra(
    @PrimaryKey(autoGenerate = true)
    val idCompra: Int = 0,
    val idUsuario: String,
    val idProducto: String,
    val nombreProducto: String,
    val cantidad: Int,
    val total: Int
)
