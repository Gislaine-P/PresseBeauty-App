package com.example.pressbeauty.model


data class LoginUI (
    val nombre : String,
    val clave : String,
    val errores2: Loginerrores = Loginerrores()
)
