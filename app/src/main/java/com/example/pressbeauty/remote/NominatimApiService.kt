package com.example.pressbeauty.remote

import com.example.pressbeauty.model.Nominatim
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/*esta interfaz define como se hace la llamada a la API y
retrofit usa esta interfaz para generar automaticamente la implementacion*/
interface NominatimApiService {
    //define una solicitud GET al endpoint de "search" de Nominatim
    @GET("search")
    fun searchLocation(
        //parametro q (direccion o texto que escribe el usuario)
        @Query("q") query: String,
        //parametro format (en json para que la respuesta sea en formato JSON)
        @Query("format") format: String = "json"
    ): Call<List<Nominatim>> //aqui retrofit devuelve lista de objetos Nominatim
}
