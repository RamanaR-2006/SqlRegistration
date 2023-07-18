package com.example.sqlregistration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class LoginView : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_view)

        dbHelper = DbHelper(this)

        val textViewUsername = findViewById<TextView>(R.id.textViewUsername)
        val textViewPassword = findViewById<TextView>(R.id.textViewPassword)

        val userCredentials = getUserCredentials()

        textViewUsername.text = "Username: ${userCredentials?.username}"
        textViewPassword.text = "Password: ${userCredentials?.password}"

    }

    private fun getUserCredentials(): UserCredentials? {
        val db = dbHelper.readableDatabase
        val columns = arrayOf(DbHelper.COLUMN_USERNAME, DbHelper.COLUMN_PASSWORD)
        val cursor = db.query(
            DbHelper.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null
        )
        var userCredentials: UserCredentials? = null
        with(cursor) {
            if (moveToFirst()) {
                val username = getString(getColumnIndexOrThrow(DbHelper.COLUMN_USERNAME))
                val password = getString(getColumnIndexOrThrow(DbHelper.COLUMN_PASSWORD))
                userCredentials = UserCredentials(username, password)
            }
            close()
        }
        return userCredentials
    }
}

