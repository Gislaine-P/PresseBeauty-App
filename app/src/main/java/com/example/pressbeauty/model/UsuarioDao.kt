package com.example.pressbeauty.model
import androidx.room.*
import androidx.room.OnConflictStrategy

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios ORDER BY id DESC")
    suspend fun obtenerUsuarios(): List<UsuarioUI>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(usuario: UsuarioUI)

    @Delete
    suspend fun eliminar(usuario: UsuarioUI)

    @Query("SELECT * FROM usuarios WHERE correo = :correo AND clave = :clave LIMIT 1")
    suspend fun login(correo: String, clave: String): UsuarioUI?
}
