package com.example.pressbeauty.model

data class Userbackend2(
    val id: Int? = null,
    val nombre: String,
    val apellido: String,
    val username: String,
    val correo: String,
    val password: String,
    val direccion: String,
    val rol: String = "usuario"
)
