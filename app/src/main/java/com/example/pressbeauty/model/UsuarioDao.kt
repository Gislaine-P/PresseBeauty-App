package com.example.pressbeauty.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsuarioDao{
    @Query("SELECT * FROM usuarios ORDER BY id DESC")
    suspend fun obtenerUsuarios(): List<Usuariobase>
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertar(usuario: Usuariobase)

    @Delete
    suspend fun eliminar(usuario: Usuariobase)

    @Query("SELECT * FROM usuarios WHERE nombre = :nombre AND clave = :clave LIMIT 1")
    suspend fun obtenerUsuarioPorCredenciales(nombre: String, clave: String): Usuariobase?
}