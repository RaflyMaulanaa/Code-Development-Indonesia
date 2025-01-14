package com.example.codeidapp.data.source.local.preferences

import android.content.Context
import com.example.codeidapp.data.source.local.model.User

class UserPreferenceManager (context: Context) {

    private val dbHelper = UserDatabaseHelper(context)

    fun saveUser(username: String, password: String): Long {
        return dbHelper.addUser(username, password)
    }

    fun login(username: String, password: String): Boolean {
        return dbHelper.checkLogin(username, password)
    }

    fun updateUser(username: String, newUsername: String, newPassword: String): Boolean {
        return dbHelper.updateUser(username, newUsername, newPassword)
    }

    fun getUser(): User? {
        return dbHelper.getUser()
    }
}