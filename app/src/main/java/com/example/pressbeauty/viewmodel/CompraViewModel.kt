package com.example.pressbeauty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pressbeauty.model.Compra
import com.example.pressbeauty.repository.CompraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CompraViewModel(
    private val repository: CompraRepository
) : ViewModel() {

    private val _comprasUsuario = MutableStateFlow<List<Compra>>(emptyList())
    val comprasUsuario: StateFlow<List<Compra>> = _comprasUsuario

    fun registrarCompra(
        idUsuario: String,
        idProducto: String,
        nombreProducto: String,
        cantidad: Int,
        total: Int
    ) {
        viewModelScope.launch {
            val compra = Compra(
                idUsuario = idUsuario,
                idProducto = idProducto,
                nombreProducto = nombreProducto,
                cantidad = cantidad,
                total = total
            )
            repository.registrarCompra(compra)
        }
    }

    fun cargarComprasDe(idUsuario: String) {
        viewModelScope.launch {
            val lista = repository.listarComprasDeUsuario(idUsuario)
            _comprasUsuario.value = lista
        }
    }
}
