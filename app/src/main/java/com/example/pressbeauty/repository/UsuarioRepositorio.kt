package com.example.pressbeauty.repository

import com.example.pressbeauty.model.UsuarioDao
import com.example.pressbeauty.model.Usuariobase

class UsuarioRepositorio(private val dao: UsuarioDao) {

    suspend fun insertar(usuario: Usuariobase) = dao.insertar(usuario)

    suspend fun obtenerUsuarios(): List<Usuariobase> = dao.obtenerUsuarios()

    suspend fun eliminar(usuario: Usuariobase) = dao.eliminar(usuario)

    suspend fun obtenerUsuarioPorCredenciales(username: String, clave: String): Usuariobase? {
        return dao.obtenerUsuarioPorCredenciales(username, clave)
    }

    suspend fun clear() = dao.clear()

    suspend fun getUser(): Usuariobase? = dao.getUser()
}
