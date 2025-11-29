package com.example.pressbeauty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pressbeauty.model.Userbackend2
import com.example.pressbeauty.model.Usuariobase
import com.example.pressbeauty.network.RetrofitClient
import com.example.pressbeauty.network.UserApi
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

    private val api = RetrofitClient.instance.create(UserApi::class.java)

    private val _estado = MutableStateFlow(
        UsuarioUI(
            idUsuario = "",
            username = "",
            nombre = "",
            apellido = "",
            correo = "",
            direccion = "",
            clave = "",
            repClave = ""
        )
    )
    val estado: StateFlow<UsuarioUI> = _estado

    fun onNombreChange(v: String) { _estado.update { it.copy(nombre = v, errores = it.errores.copy(nombre = null)) } }
    fun onApellidoChange(v: String) { _estado.update { it.copy(apellido = v, errores = it.errores.copy(apellido = null)) } }
    fun onUsernameChange(v: String) { _estado.update { it.copy(username = v, errores = it.errores.copy(username = null)) } }
    fun onCorreoChange(v: String) { _estado.update { it.copy(correo = v, errores = it.errores.copy(correo = null)) } }
    fun onDireccionChange(v: String) { _estado.update { it.copy(direccion = v, errores = it.errores.copy(direccion = null)) } }
    fun onClaveChange(v: String) { _estado.update { it.copy(clave = v, errores = it.errores.copy(clave = null)) } }
    fun onRepClaveChange(v: String) { _estado.update { it.copy(repClave = v, errores = it.errores.copy(repClave = null)) } }
    fun onAceptarTerminosChange(v: Boolean) { _estado.update { it.copy(aceptaTerminos = v) } }

    fun validarFormulario(): Boolean {
        val e = estado.value
        val errores = UsuarioErrores(
            nombre = if (e.nombre.isBlank()) "NO PUEDE ESTAR VACÍO" else null,
            apellido = if (e.apellido.isBlank()) "NO PUEDE ESTAR VACÍO" else null,
            username = if (e.username.isBlank()) "NO PUEDE ESTAR VACÍO" else null,
            correo = if (!e.correo.contains("@")) "CORREO INVÁLIDO" else null,
            direccion = if (e.direccion.isBlank()) "NO PUEDE ESTAR VACÍO" else null,
            clave = if (e.clave.length < 8) "DEBE TENER AL MENOS 8 CARACTERES" else null,
            repClave = if (e.repClave != e.clave) "LAS CLAVES NO COINCIDEN" else null
        )
        val hayErrores = listOfNotNull(
            errores.nombre, errores.apellido, errores.username, errores.correo,
            errores.direccion, errores.clave, errores.repClave
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }
        return !hayErrores
    }

    fun guardarUsuario() {
        val e = estado.value
        if (!validarFormulario()) return

        viewModelScope.launch {

            val remoto = Userbackend2(
                id = null,
                nombre = e.nombre,
                apellido = e.apellido,
                username = e.username,
                correo = e.correo,
                password = e.clave,
                direccion = e.direccion,
                rol = "usuario"
            )

            try {
                val creado = api.createUser(remoto)
                println("🔥 Usuario creado en backend: $creado")

                val local = Usuariobase(
                    username = creado.username,
                    nombre = creado.nombre,
                    apellido = creado.apellido,
                    correo = creado.correo,
                    clave = creado.password,
                    direccion = creado.direccion,
                    aceptaTerminos = e.aceptaTerminos
                )

                repositorio.clear()
                repositorio.insertar(local)
                sesionDataStore.guardarSesionActiva(true)

            } catch (e: Exception) {
                println("❌ ERROR AL CREAR USUARIO EN BACKEND")
                e.printStackTrace()
            }
        }
    }

    fun iniciarSesion(username: String, clave: String, onResultado: (Boolean) -> Unit) {
        viewModelScope.launch {

            val req = Userbackend2(
                id = null,
                nombre = "",
                apellido = "",
                username = username,
                correo = "",
                password = clave,
                direccion = "",
                rol = "usuario"
            )

            try {
                val logged = api.login(req)
                println("🔥 Login backend: $logged")

                val local = Usuariobase(
                    username = logged.username,
                    nombre = logged.nombre,
                    apellido = logged.apellido,
                    correo = logged.correo,
                    clave = clave,
                    direccion = logged.direccion,
                    aceptaTerminos = true
                )

                repositorio.clear()
                repositorio.insertar(local)
                sesionDataStore.guardarSesionActiva(true)

                onResultado(true)

            } catch (e: Exception) {
                println("❌ ERROR LOGIN BACKEND")
                e.printStackTrace()
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
            repositorio.clear()
            _estado.update {
                it.copy(
                    nombre = "",
                    apellido = "",
                    username = "",
                    correo = "",
                    direccion = "",
                    clave = "",
                    aceptaTerminos = false
                )
            }
        }
    }
}
