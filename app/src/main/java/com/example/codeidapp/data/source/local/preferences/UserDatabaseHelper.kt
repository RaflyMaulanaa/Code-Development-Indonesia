package com.example.codeidapp.data.source.local.preferences

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.codeidapp.data.source.local.model.User

class UserDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "user_login_db"
        private const val DATABASE_VERSION = 1
        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_USERS_TABLE = "CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_USERNAME TEXT, " +
                "$COLUMN_PASSWORD TEXT)"
        db?.execSQL(CREATE_USERS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun addUser(username: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
        }
        val id = db.insert(TABLE_USERS, null, values)
        db.close()
        return id
    }

    fun updateUser(username: String, newUsername: String, newPassword: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, newUsername)
            put(COLUMN_PASSWORD, newPassword)
        }
        val result = db.update(
            TABLE_USERS,
            values,
            "$COLUMN_USERNAME=?",
            arrayOf(username)
        )
        db.close()
        return result > 0
    }

    fun checkLogin(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_USERS,
            null,
            "$COLUMN_USERNAME=? AND $COLUMN_PASSWORD=?",
            arrayOf(username, password),
            null, null, null
        )
        val isValid = cursor.count > 0
        cursor.close()
        db.close()
        return isValid
    }

    fun getUser(): User? {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_USERNAME, COLUMN_PASSWORD),
            null, null, null, null, null
        )
        var user: User? = null
        if (cursor.moveToFirst()) {
            val username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
            val password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
            user = User(username, password)
        }
        cursor.close()
        db.close()
        return user
    }
}