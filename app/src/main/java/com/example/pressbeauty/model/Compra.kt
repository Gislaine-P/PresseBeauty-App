package com.example.pressbeauty.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Tabla de compras realizadas
@Entity(tableName = "compras")
data class Compra(
    @PrimaryKey(autoGenerate = true)
    val idCompra: Int = 0,

    // quién compró
    val idUsuario: String,

    // qué compró
    val idProducto: String,
    val nombreProducto: String,

    // cuántas unidades
    val cantidad: Int,

    // total final pagado en esa compra (ej: 34000)
    val total: Int
)
