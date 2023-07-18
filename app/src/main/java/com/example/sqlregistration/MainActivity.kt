package com.example.sqlregistration

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DbHelper(this)

        var username = findViewById<EditText>(R.id.username)
        var password = findViewById<EditText>(R.id.password)
        var button = findViewById<Button>(R.id.button1)

        button.setOnClickListener {
            var username1 = username.text.toString()
            var password1 = password.text.toString()
            if (username1.isEmpty()) {
                Toast.makeText(this,"Username is empty",Toast.LENGTH_SHORT)
            }
            else if(password1.isEmpty()){
                Toast.makeText(this,"Password is empty",Toast.LENGTH_SHORT)
            }
            else{
                insertUserCredentials(username1, password1)
                Toast.makeText(this,"Accepted",Toast.LENGTH_SHORT)
                val intent = Intent(this, LoginView::class.java)
                startActivity(intent)
            }
        }
    }
    private fun insertUserCredentials(username: String, password: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DbHelper.COLUMN_USERNAME, username)
            put(DbHelper.COLUMN_PASSWORD, password)
        }
        db.insert(DbHelper.TABLE_NAME, null, values)
    }
}