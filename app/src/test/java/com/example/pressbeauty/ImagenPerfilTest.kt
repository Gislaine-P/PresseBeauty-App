package com.example.pressbeauty

import android.net.Uri
import com.example.pressbeauty.model.ImagenPerfil
import org.junit.Assert.*
import org.junit.Test

class ImagenPerfilTest {

    @Test
    fun crearImagenPerfil() {
        val perfil = ImagenPerfil(
            idUsuarioUI = "10",
            imagenUri = null
        )

        assertEquals("10", perfil.idUsuarioUI)
        assertNull(perfil.imagenUri)
    }
}