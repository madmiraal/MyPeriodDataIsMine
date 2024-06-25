package com.myperioddataismine

import android.content.Context
import com.myperioddataismine.DatabaseHelper.Companion.DB_FILENAME
import net.zetetic.database.sqlcipher.SQLiteDatabase

class EncryptedDatabase(private val context: Context?) {
    private lateinit var database: SQLiteDatabase
    private lateinit var databaseHelper: DatabaseHelper

    init {
        System.loadLibrary("sqlcipher")
    }

    fun exists() : Boolean {
        if (context == null) {
            return false
        }
        return context.getDatabasePath(DB_FILENAME).exists()
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
