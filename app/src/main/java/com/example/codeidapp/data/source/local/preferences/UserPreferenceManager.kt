package com.example.codeidapp.data.source.local.preferences

import android.content.Context
import com.example.codeidapp.data.source.local.model.User

class UserPreferenceManager (context: Context) {

    private val preferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveUser(username: String, password: String) : Boolean {
        preferences.edit()
            .putString("username", username)
            .putString("password", password)
            .apply()
        return true
    }

    fun getUser(): User? {
        val username = preferences.getString("username", null)
        val password = preferences.getString("password", null)
        return if (username != null && password != null) {
            User(username, password)
        } else {
            null
        }
    }

    fun login(username: String, password: String): Boolean {
        val savedUser = getUser()
        return savedUser?.let {
            it.username == username && it.password == password
        } ?: false
    }
}