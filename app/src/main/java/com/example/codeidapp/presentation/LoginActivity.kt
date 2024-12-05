package com.example.codeidapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.codeidapp.databinding.ActivityLoginBinding
import com.example.codeidapp.viewmodel.LoginViewModel
import com.example.codeidapp.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory.getInstance(this))[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.preffiledAccount("Rafly", "123456")

        binding.btnLogin.setOnClickListener {
            doLogin()
        }

    }

    private fun doLogin() {

        val result = viewModel.login(
            binding.etUsername.text.toString(),
            binding.etPassword.text.toString()
        )

        if (result) {
            Toast.makeText(this, "Benar", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Salah", Toast.LENGTH_SHORT).show()
        }

    }
}