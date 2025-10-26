package com.example.pressbeauty.repository

import com.example.pressbeauty.model.UsuarioUI
import com.example.pressbeauty.model.UsuarioDao
class UsuarioRepositorio (private val dao: UsuarioDao) {
    suspend fun obtenerUsuarios(): List<UsuarioUI> = dao.obtenerUsuarios()
    suspend fun insertar(usuario: UsuarioUI) = dao.insertar(usuario)
    suspend fun eliminar(usuario: UsuarioUI) = dao.eliminar(usuario)
}