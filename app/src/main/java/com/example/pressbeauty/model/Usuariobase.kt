package com.example.pressbeauty.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuariobase (
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val nombre : String,
    val apellido : String,
    val correo : String,
    val direccion : String,
    val clave : String,
    val aceptaTerminos : Boolean
)