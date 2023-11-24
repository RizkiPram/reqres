package com.example.myapplication.data.local

data class UserModel(
    var email:String,
    var password:String,
    var token:String,
    var isLogin:Boolean
)