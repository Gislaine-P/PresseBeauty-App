package com.example.pressbeauty.model

import android.net.Uri

data class UsuarioUI(
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
