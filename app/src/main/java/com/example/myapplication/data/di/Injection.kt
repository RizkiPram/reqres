package com.example.myapplication.data.di

import android.content.Context
import com.example.myapplication.data.remote.ApiConfig
import com.example.myapplication.repository.UserRepository

object Injection {
    fun provideRepository(context:Context):UserRepository{
        val apiService = ApiConfig.getApiService()
        return UserRepository(apiService)
    }
}