package com.example.myapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.local.UserPreferences

import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.viewmodel.LoginViewModel
import com.example.myapplication.viewmodel.ViewModelFactory

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel:LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        binding.apply {

        }
    }
    private fun setupViewModel(){
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[LoginViewModel::class.java]
        binding.apply {
            btnLogin.setOnClickListener {
                val email=etEmail.text.toString()
                val password=etPassword.text.toString()
                textLayoutEmail.error=null
                textLayoutPassword.error=null
                if (!isValidEmail(email)){
                    textLayoutEmail.error = "Error"
                }
                if (password.length<8){
                    textLayoutPassword.error="Password Minimal 8"
                }
                if (isValidEmail(email) && password.length>8){
                   viewModel.login(email, password)
                    Log.d("LOGIN","Success")
                }
            }
        }
        viewModel.getUser().observe(this@LoginActivity){
            if (it.isLogin){
                startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                finish()
            }
        }
    }
    private fun isValidEmail(email:String):Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}