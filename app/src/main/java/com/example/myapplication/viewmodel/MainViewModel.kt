package com.example.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.data.di.Injection
import com.example.myapplication.data.remote.DataItem
import com.example.myapplication.repository.UserRepository
import java.lang.IllegalArgumentException

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
//    private val _listUser = MutableLiveData<List<DataItem>>()
//    val listUser: MutableLiveData<List<DataItem>> = _listUser

    val listUser:LiveData<PagingData<DataItem>> = userRepository.getListUser().cachedIn(viewModelScope)
//    fun getListUser() {
//        val client=ApiConfig.getApiService().getUser()
//        client.enqueue(object : Callback<UserResponse>{
//            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//                val responseBody=response.body()
//                if (response.isSuccessful){
//                    if (responseBody!=null){
//                        _listUser.value=responseBody.data
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//                Log.d("MainViewModel","OnFailure${t.message}")
//            }
//        })
//    }

    //    fun logout() {
//        viewModelScope.launch {
//            pref.logout()
//        }
//    }
//    fun getUser(): LiveData<UserModel> {
//        return pref.getUser().asLiveData()
//    }
    class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(Injection.provideRepository(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel")
        }
    }
}