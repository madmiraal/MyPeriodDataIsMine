package com.myperioddataismine

import android.content.Context
import net.zetetic.database.sqlcipher.SQLiteDatabase

class EncryptedDatabase(private val context: Context?) {
    private lateinit var database: SQLiteDatabase
    private lateinit var databaseHelper: DatabaseHelper

    init {
        System.loadLibrary("sqlcipher")
    }

    fun open(passcode: String) {
        databaseHelper = DatabaseHelper(context, passcode)
        database = databaseHelper.writableDatabase
    }

    fun close() {
        database.close()
    }

    fun delete() {
        databaseHelper.delete()
    }
}
