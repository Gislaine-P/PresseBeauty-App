package com.example.pressbeauty.network

import com.example.pressbeauty.model.Userbackend2
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
interface UserApi {
    @GET("api/v1/users")
    suspend fun getAllUsers(): List<Userbackend2>

    @POST("api/v1/users/login")
    suspend fun login(@Body user: Userbackend2): Userbackend2

    @POST("api/v1/users/add")
    suspend fun createUser(@Body user: Userbackend2): Userbackend2

    @POST("api/v1/users/update")
    suspend fun updateUser(@Body user: Userbackend2): Userbackend2

    @DELETE("api/v1/users/delete/{username}")
    suspend fun deleteUser(@Path("username") username: String)
}