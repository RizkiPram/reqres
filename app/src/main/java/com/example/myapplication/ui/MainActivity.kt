package com.example.myapplication.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.UserAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels { MainViewModel.ViewModelFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setupViewModel()
        binding.rvListUser.layoutManager = LinearLayoutManager(this)
        setupListData()
    }

    private fun setupViewModel() {

//        viewModel = ViewModelProvider(
//            this,
//            ViewModelFactory(UserPreferences.getInstance(dataStore))
//        )[MainViewModel::class.java]
//        viewModel.getUser().observe(this@MainActivity){
//            if (!it.isLogin){
//                startActivity(Intent(this@MainActivity,LoginActivity::class.java))
//                finish()
//            }
//        }
//        viewModel.getListUser()
//        viewModel.listUser.observe(this){
//            setupListData(it)
//        }
    }

    private fun setupListData() {
        val userAdapter = UserAdapter()
        binding.rvListUser.adapter = userAdapter
        viewModel.listUser.observe(this@MainActivity) {
            userAdapter.submitData(lifecycle,it)
        }
    }

}