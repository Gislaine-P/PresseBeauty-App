package com.example.pressbeauty.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pressbeauty.model.UsuarioErrores
import com.example.pressbeauty.model.UsuarioUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update


class UsuarioViewModel : ViewModel() {



    private val _estado = MutableStateFlow(
        UsuarioUI(
            idUsuario = "",
            nombre = "",
            apellido = "",
            correo = "",
            direccion = "",
            clave = "",
            repClave = ""
        )
    )

    val estado: StateFlow<UsuarioUI> = _estado

    fun setNombreUsuario(nombre: String) {
        _estado.update { it.copy(nombre = nombre) }
    }
    fun onNombreChange (valor : String){
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }
    fun onApellidoChange (valor: String){
        _estado.update { it.copy(apellido = valor, errores = it.errores.copy(apellido = null)) }
    }
    fun onCorreoChange (valor: String){
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null))}
    }
    fun onDireccionChange (valor : String) {
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }
    fun onClaveChange (valor : String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }
    fun onRepClaveChange (valor : String) {
        _estado.update { it.copy(repClave = valor, errores = it.errores.copy(repClave = null)) }
    }

    fun onAceptarTerminosChange(valor : Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor) }
    }
    fun validarFormulario(): Boolean{
        val estadoActual = estado.value
        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "NO PUEDE ESTAR VACÍO" else null,
            apellido = if (estadoActual.apellido.isBlank()) "NO PUEDE ESTAR VACÍO" else null,
            correo = if (!estadoActual.correo.contains("@")) "CORREO INVÁLIDO" else null,
            direccion = if (estadoActual.direccion.isBlank()) "NO PUEDE ESTAR VACÍO" else null,
            clave = if (estadoActual.clave.length < 8) "DEBE TENER AL MENOS 8 CARACTERES" else null,
            repClave = if (estadoActual.repClave != estadoActual.clave) "LAS CLAVES NO COINCIDEN" else null,
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.apellido,
            errores.correo,
            errores.direccion,
            errores.clave,
            errores.repClave
        ).isNotEmpty()

        _estado.update { it.copy(errores=errores) }

        return !hayErrores
    }
    


}