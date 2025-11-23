package com.example.pressbeauty.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

//obejto que provee la instancia de Retrofit para llamar a la API
object RetrofitInstance {



    /*
    se agrega un interceptor al cliente HTTP para incluir el User-Agent personalizado.
    Esto evitar bloqueos, pq si se usa un identificador generico de retrofit o no es definido
    Nominatim puede rechazar o limital el acceso, es parte de sus terminos de usos, lo pide explicitamente en su documentacion*/
    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                //Definimos con nombre de app y contacto (necesario poner el real)
                .header("User-Agent", "pressbeauty-app (contacto: gis.paredes@duocuc.cl)")
                .build()
            chain.proceed(request)
        }
        .build()

    /*Si no lo usamos puede haber un riesgo de que las llamadas sean rechazadas, devuelva respuestas con errores o vacias
    */


    /* Retrofit con el url de API externa usada (Nominatim), usa cliente con el identificador definido anteriorment
    y el gson mapea JSON a objetos kotlin
     */
    val api : NominatimApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://nominatim.openstreetmap.org/")
            .client(client) // se usa cliente con User-Agent
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NominatimApiService::class.java) //implementa la interfaz NominatimApiService
    }
}