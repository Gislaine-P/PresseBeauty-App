package com.example.pressbeauty

import  com.example.pressbeauty.model.Userbackend2
import com.example.pressbeauty.model.Usuariobase
import com.example.pressbeauty.network.UserApi
import com.example.pressbeauty.repository.SesionDataStore
import com.example.pressbeauty.repository.UsuarioRepositorio
import com.example.pressbeauty.viewmodel.UsuarioViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class UsuarioViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var repo: UsuarioRepositorio
    private lateinit var dataStore: SesionDataStore
    private lateinit var api: UserApi
    private lateinit var viewModel: UsuarioViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        repo = mock()
        dataStore = mock()
        api = mock()
        viewModel = UsuarioViewModel(repo, dataStore)
        val apiField = UsuarioViewModel::class.java.getDeclaredField("api")
        apiField.isAccessible = true
        apiField.set(viewModel, api)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun validarFormularioConCamposVaciosDebeFallar() {
        val valido = viewModel.validarFormulario()
        assertFalse(valido)
    }

    @Test
    fun onNombreChangeActualizaEstado() {
        viewModel.onNombreChange("Marta")
        assertEquals("Marta", viewModel.estado.value.nombre)
    }

    @Test
    fun onCorreoChangeLimpiaError() {
        viewModel.onCorreoChange("correo_invalido")
        viewModel.validarFormulario()
        viewModel.onCorreoChange("valid@mail.com")
        assertEquals("valid@mail.com", viewModel.estado.value.correo)
    }

    @Test
    fun guardarUsuarioCorrectamente() = runTest {
        viewModel.onNombreChange("Marta")
        viewModel.onApellidoChange("Ugarte")
        viewModel.onUsernameChange("marta")
        viewModel.onCorreoChange("test@mail.com")
        viewModel.onDireccionChange("Mi casa")
        viewModel.onClaveChange("12345678")
        viewModel.onRepClaveChange("12345678")
        viewModel.onAceptarTerminosChange(true)

        val backendReturn = Userbackend2(
            id = null ,
            nombre = "Marta",
            apellido = "Ugarte",
            username = "marta",
            correo = "test@mail.com",
            password = "12345678",
            direccion = "Mi casa",
            rol = "usuario"
        )

        whenever(api.createUser(any())).thenReturn(backendReturn)

        viewModel.guardarUsuario()
        advanceUntilIdle()

        verify(repo).clear()
        verify(repo).insertar(any())
        verify(dataStore).guardarSesionActiva(true)
    }

    @Test
    fun iniciarSesionExitoso() = runTest {
        val backendReturn = Userbackend2(
            id  = null,
            nombre = "Marta",
            apellido = "Ugarte",
            username = "marta",
            correo = "correo@mail.com",
            password = "12345678",
            direccion = "Mi casa",
            rol = "usuario"
        )

        whenever(api.login(any())).thenReturn(backendReturn)

        var resultado = false

        viewModel.iniciarSesion("marta", "12345678") {
            resultado = it
        }

        advanceUntilIdle()

        assertTrue(resultado)
        verify(repo).insertar(any())
        verify(dataStore).guardarSesionActiva(true)
    }

    @Test
    fun iniciarSesionFallido() = runTest {
        whenever(api.login(any())).thenThrow(RuntimeException("Error login"))

        var resultado = true

        viewModel.iniciarSesion("marta", "wrong") {
            resultado = it
        }

        advanceUntilIdle()

        assertFalse(resultado)
    }
}
