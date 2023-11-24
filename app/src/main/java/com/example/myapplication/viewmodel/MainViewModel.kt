package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.UserPreferences
import kotlinx.coroutines.launch

class MainViewModel(private val pref:UserPreferences):ViewModel() {
    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

}