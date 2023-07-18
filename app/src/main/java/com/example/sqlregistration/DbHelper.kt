package com.example.sqlregistration
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "user_credentials.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "user_table"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE IF NOT EXISTS $TABLE_NAME " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_USERNAME TEXT NOT NULL, " +
                "$COLUMN_PASSWORD TEXT NOT NULL);"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertUserCredentials(username: String, password: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
        }
        db.insert(TABLE_NAME, null, values)
    }

    fun getUserCredentials(): UserCredentials? {
        val db = readableDatabase
        val columns = arrayOf(COLUMN_USERNAME, COLUMN_PASSWORD)
        val cursor = db.query(
            TABLE_NAME,
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
                val username = getString(getColumnIndexOrThrow(COLUMN_USERNAME))
                val password = getString(getColumnIndexOrThrow(COLUMN_PASSWORD))
                userCredentials = UserCredentials(username, password)
            }
            close()
        }
        return userCredentials
    }
}
