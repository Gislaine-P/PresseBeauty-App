package com.example.pressbeauty.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "usuarios")
data class UsuarioUI(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idUsuario : String,
    val imagenUri : Uri? = null,
    val nombre : String = "",
    val apellido : String = "",
    val correo : String = "",
    val direccion : String = "",
    val clave : String = "",
    val repClave : String = "",
    val aceptaTerminos : Boolean = false,
    val errores : UsuarioErrores = UsuarioErrores()
)
