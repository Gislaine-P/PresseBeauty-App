package com.example.pressbeauty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pressbeauty.model.Usuariobase
import com.example.pressbeauty.repository.UsuarioRepositorio
import com.example.pressbeauty.repository.SesionDataStore
import com.example.pressbeauty.view.UsuarioErrores
import com.example.pressbeauty.view.UsuarioUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val repositorio: UsuarioRepositorio,
    private val sesionDataStore: SesionDataStore
) : ViewModel() {

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

    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onApellidoChange(valor: String) {
        _estado.update { it.copy(apellido = valor, errores = it.errores.copy(apellido = null)) }
    }

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onDireccionChange(valor: String) {
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    fun onRepClaveChange(valor: String) {
        _estado.update { it.copy(repClave = valor, errores = it.errores.copy(repClave = null)) }
    }

    fun onAceptarTerminosChange(valor: Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    fun validarFormulario(): Boolean {
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
        _estado.update { it.copy(errores = errores) }
        return !hayErrores
    }

    fun guardarUsuario() {
        val estadoActual = estado.value
        if (validarFormulario()) {
            viewModelScope.launch {
                val nuevoUsuario = Usuariobase(
                    nombre = estadoActual.nombre,
                    apellido = estadoActual.apellido,
                    correo = estadoActual.correo,
                    clave = estadoActual.clave,
                    direccion = estadoActual.direccion,
                    aceptaTerminos = estadoActual.aceptaTerminos
                )
                repositorio.insertar(nuevoUsuario)
            }
        }
    }
    fun limpiarDatos() {
        viewModelScope.launch {
            sesionDataStore.guardarSesionActiva(false)
        }
        _estado.value = _estado.value.copy(
            nombre = "",
            apellido = "",
            correo = "",
            direccion = "",
            clave = "",
            aceptaTerminos = false
        )
    }
    fun iniciarSesion(nombre: String, clave: String, onResultado: (Boolean) -> Unit) {
        viewModelScope.launch {
            val usuario = repositorio.obtenerUsuarioPorCredenciales(nombre, clave)
            if (usuario != null) {
                _estado.update {
                    it.copy(
                        nombre = usuario.nombre,
                        apellido = usuario.apellido,
                        correo = usuario.correo,
                        direccion = usuario.direccion,
                        clave = usuario.clave,
                        aceptaTerminos = usuario.aceptaTerminos
                    )
                }
                sesionDataStore.guardarSesionActiva(true)
                onResultado(true)
            } else {
                onResultado(false)
            }
        }
    }
    suspend fun estaLogueado(): Boolean {
        return sesionDataStore.sesionIniciada.first()
    }
    fun cerrarSesion() {
        viewModelScope.launch {
            sesionDataStore.guardarSesionActiva(false)
            _estado.update {
                it.copy(
                    nombre = "",
                    apellido = "",
                    correo = "",
                    direccion = "",
                    clave = "",
                    aceptaTerminos = false
                )
            }
        }
    }
}
