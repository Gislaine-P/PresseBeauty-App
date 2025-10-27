package com.example.pressbeauty.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pressbeauty.model.Compra

@Dao
interface CompraDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCompra(compra: Compra)
    @Query("SELECT * FROM compras ORDER BY idCompra DESC")
    suspend fun obtenerCompras(): List<Compra>
    @Query("SELECT * FROM compras WHERE idUsuario = :idUsuario ORDER BY idCompra DESC")
    suspend fun obtenerComprasPorUsuario(idUsuario: String): List<Compra>
}
