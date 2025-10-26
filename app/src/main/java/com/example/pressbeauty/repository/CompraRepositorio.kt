package com.example.pressbeauty.repository

import com.example.pressbeauty.model.Compra

class CompraRepository(private val compraDao: CompraDao) {

    suspend fun registrarCompra(compra: Compra) {
        compraDao.insertarCompra(compra)
    }

    suspend fun listarCompras(): List<Compra> {
        return compraDao.obtenerCompras()
    }

    suspend fun listarComprasDeUsuario(idUsuario: String): List<Compra> {
        return compraDao.obtenerComprasPorUsuario(idUsuario)
    }
}
