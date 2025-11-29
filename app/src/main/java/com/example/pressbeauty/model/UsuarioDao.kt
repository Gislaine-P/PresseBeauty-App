package com.example.pressbeauty.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuarios ORDER BY id DESC")
    suspend fun obtenerUsuarios(): List<Usuariobase>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(usuario: Usuariobase)

    @Delete
    suspend fun eliminar(usuario: Usuariobase)

    @Query("SELECT * FROM usuarios WHERE username = :username AND clave = :clave LIMIT 1")
    suspend fun obtenerUsuarioPorCredenciales(username: String, clave: String): Usuariobase?

    @Query("DELETE FROM usuarios")
    suspend fun clear()

    @Query("SELECT * FROM usuarios LIMIT 1")
    suspend fun getUser(): Usuariobase?
}
