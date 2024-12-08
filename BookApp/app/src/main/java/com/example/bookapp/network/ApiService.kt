package com.example.bookapp.network


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/auth/login")
    fun login(@Body user: User): Call<String>

    @POST("/api/auth/signup")
    fun register(@Body user: User): Call<String>
}
