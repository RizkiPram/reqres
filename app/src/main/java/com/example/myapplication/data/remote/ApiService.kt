package com.example.myapplication.data.remote

import com.example.myapplication.data.LoginInformation
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun login(@Body loginInformation: LoginInformation) : Call<LoginResponse>

    @GET("users")
    fun getUser():Call<UserResponse>
}