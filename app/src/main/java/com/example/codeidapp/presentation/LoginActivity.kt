package com.example.codeidapp.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.codeidapp.MainActivity
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

        binding.btnLogin.setOnClickListener {
            doLogin()
        }

    }

    private fun doLogin() {

        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        val result = viewModel.login(
            binding.etUsername.text.toString(),
            binding.etPassword.text.toString()
        )

        if (result) {
            Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val id = saveNewUser(username, password)
            Log.d("SaveUser", "User ID: $id")
        }

    }

    private fun saveNewUser(username: String, password: String) {
        val id = viewModel.saveUser(username, password)
        if (id != -1L) {
            Toast.makeText(this, "Akun Baru Terdaftar!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Gagal menyimpan akun", Toast.LENGTH_SHORT).show()
        }
    }

}