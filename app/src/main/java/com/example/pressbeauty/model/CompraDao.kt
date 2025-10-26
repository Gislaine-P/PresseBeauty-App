package com.example.pressbeauty.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pressbeauty.model.Compra

@Dao
interface CompraDao {

    // Insertar una compra nueva
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCompra(compra: Compra)

    // Listar TODAS las compras (últimas primero)
    @Query("SELECT * FROM compras ORDER BY idCompra DESC")
    suspend fun obtenerCompras(): List<Compra>

    // Listar las compras hechas por un usuario específico
    @Query("SELECT * FROM compras WHERE idUsuario = :idUsuario ORDER BY idCompra DESC")
    suspend fun obtenerComprasPorUsuario(idUsuario: String): List<Compra>
}
