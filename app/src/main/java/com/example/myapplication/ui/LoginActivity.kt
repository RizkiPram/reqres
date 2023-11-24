package com.example.myapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.data.local.UserPreferences
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.viewmodel.LoginViewModel
import com.example.myapplication.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        binding.apply {

        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[LoginViewModel::class.java]
        binding.apply {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                textLayoutEmail.error = null
                textLayoutPassword.error = null
                if (!isValidEmail(email)) {
                    textLayoutEmail.error = "Error"
                }
                if (password.length < 8) {
                    textLayoutPassword.error = "Password Minimal 8"
                }
                viewModel.login(email, password)
            }
        }
        viewModel.isSuccess.observe(this@LoginActivity) {
            if (!it) {
                showSnackBar()
            }
        }
        viewModel.getUser().observe(this@LoginActivity) {
            if (it.isLogin) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showSnackBar() {
        val snackbar =
            Snackbar.make(binding.root, "Email Atau Password Salah", Snackbar.LENGTH_LONG)
        val layoutParams = snackbar.view.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP
        snackbar.view.layoutParams = layoutParams
        val snackbarView = snackbar.view
        snackbar.setBackgroundTint(resources.getColor(R.color.red_alert))
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        snackbarView.setBackgroundColor(resources.getColor(R.color.red_alert, null))
        textView.setTextColor(resources.getColor(R.color.white))
        textView.textSize = 16f
        snackbar.show()
    }
}