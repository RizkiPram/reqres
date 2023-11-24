package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.ApiConfig
import com.example.myapplication.data.LoginInformation
import com.example.myapplication.data.local.UserModel
import com.example.myapplication.data.local.UserPreferences
import com.example.myapplication.data.remote.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel (private val pref: UserPreferences):ViewModel() {
    fun login(email:String,password:String){
        val loginInformation=LoginInformation(email,password)
        val client= ApiConfig.getApiService().login(loginInformation)
        client.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseBody=response.body()
                if (response.isSuccessful){
                    if (responseBody!=null){
                        val userModel=UserModel(
                            email,
                            password,
                            responseBody.token,
                            true
                        )
                        saveUser(userModel)
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d(TAG,"onFailure:${t.message}")
            }
        })
    }
    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
    companion object{
        const val TAG="LOGINVIEWMODEL"
    }
}