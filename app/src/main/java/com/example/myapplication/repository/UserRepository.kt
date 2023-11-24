package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.myapplication.data.UserPagingSource
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.remote.DataItem

class UserRepository(private val apiService: ApiService) {
    fun getListUser():LiveData<PagingData<DataItem>>{
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {UserPagingSource(apiService)}
        ).liveData

    }
}