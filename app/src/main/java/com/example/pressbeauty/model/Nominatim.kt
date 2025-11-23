package com.example.pressbeauty.model



//clase que duvleve la API, contiene los campos que interesan
// del resultado, sirve para que retrofit sepa convertir el JSON en un objeto usable
data class Nominatim(

    val display_name : String, //nombre de la direccion
    val lat : String, //latitud de la ubicacion
    val lon : String //longitud de la ubicacion
)
