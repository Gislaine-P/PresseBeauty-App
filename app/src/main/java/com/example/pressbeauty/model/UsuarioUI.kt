package com.example.pressbeauty.model

data class UsuarioUI(
    val nombre : String = "",
    val apellido : String = "",
    val correo : String = "",
    val direccion : String = "",
    val clave : String = "",
    val repClave : String = "",
    val aceptaTerminos : Boolean = false,
    val errores : UsuarioErrores = UsuarioErrores()
)
