package com.example.pressbeauty.view

import android.net.Uri
import com.example.pressbeauty.view.UsuarioErrores

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