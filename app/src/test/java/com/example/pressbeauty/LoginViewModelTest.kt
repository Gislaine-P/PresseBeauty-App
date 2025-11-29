package com.example.pressbeauty

import com.example.pressbeauty.viewmodel.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        viewModel = LoginViewModel()
    }

    @Test
    fun validarFormularioConCamposVaciosDebeFallar() {
        val resultado = viewModel.validarFormulario()
        assertFalse(resultado)
    }

    @Test
    fun onNombreChangeActualizaEstado() {
        viewModel.onNombreChange("Marta")
        assertEquals("Marta", viewModel.estado2.value.nombre)
    }

    @Test
    fun onClaveChangeActualizaEstado() {
        viewModel.onClaveChange("1234")
        assertEquals("1234", viewModel.estado2.value.clave)
    }

    @Test
    fun validarFormularioCorrectoDebePasar() {
        viewModel.onNombreChange("Marta")
        viewModel.onClaveChange("12345678")
        val resultado = viewModel.validarFormulario()
        assertTrue(resultado)
    }

    @Test
    fun limpiarCamposDebeVaciarValores() {
        viewModel.onNombreChange("Marta")
        viewModel.onClaveChange("1234")
        viewModel.limpiarCampos()

        assertEquals("", viewModel.estado2.value.nombre)
        assertEquals("", viewModel.estado2.value.clave)
    }
}
