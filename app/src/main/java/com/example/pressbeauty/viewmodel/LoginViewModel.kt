package com.example.pressbeauty.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pressbeauty.model.LoginUI
import com.example.pressbeauty.model.Loginerrores
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel (

){
     private val _estado2 = MutableStateFlow(LoginUI(nombre = "", clave = ""))

     val estado2 : StateFlow<LoginUI> = _estado2

     fun onNombreChange (valor:String){
          _estado2.update { it.copy(nombre = valor, errores2 = it.errores2.copy(nombre = null)) }
     }
     fun onClaveChange(valor: String){
          _estado2.update { it.copy(clave = valor, errores2 = it.errores2.copy(clave = null)) }
     }
     fun validarFormulario(): Boolean{
          val estado2Actual = estado2.value
          val errores2= Loginerrores(
               nombre=if (estado2Actual.nombre.isBlank())"NO PUEDE ESTAR VACÍO" else null,
               clave =if (estado2Actual.clave.isBlank())"NO PUEDE ESTAR VACÍO" else null
          )
          val hayErrores = listOfNotNull(
               errores2.nombre,
               errores2.clave
          ).isNotEmpty()

          _estado2.update { it.copy(errores2=errores2) }

          return !hayErrores
     }
}