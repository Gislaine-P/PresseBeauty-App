package com.example.pressbeauty.repository

import android.net.Uri
import com.example.pressbeauty.model.UsuarioErrores
import com.example.pressbeauty.model.UsuarioUI
import kotlin.String

class PerfilUsuRepositorio {

    private var PerfilUsu = UsuarioUI(
    idUsuario = "",
    imagenUri = null,
    nombre = "",
    apellido = "",
    correo = "",
    direccion = "",
    clave = "",
    repClave = "",
    aceptaTerminos = false,
    errores = UsuarioErrores()
    )


    fun getProfile(): UsuarioUI = PerfilUsu

    fun updateImage(uri: Uri?){
        PerfilUsu = PerfilUsu.copy(imagenUri = uri)
    }
}