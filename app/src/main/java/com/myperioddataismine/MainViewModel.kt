package com.myperioddataismine

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import java.util.Date

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val encryptedDatabase = EncryptedDatabase(application)
    private var dayData: DayData? = null

    fun encryptedDatabaseExists(): Boolean {
        return encryptedDatabase.exists()
    }

    fun decryptedDatabaseIsOpen(): Boolean {
        return encryptedDatabase.isOpen()
    }

    fun openDatabase(passcode: String): Boolean {
        return encryptedDatabase.open(passcode)
    }

    fun deleteDatabase() {
        encryptedDatabase.delete()
        dayData = null
    }

    fun changePasscode(passcode: String) {
        encryptedDatabase.changePasscode(passcode)
    }

    fun getDayData(date: Date): DayData {
        val data = dayData ?: encryptedDatabase.getDayData(date)
        dayData = if (data.date != date) {
            encryptedDatabase.getDayData(date)
        } else {
            data
        }
        return data
    }

    fun saveDayData(dayData: DayData) {
        encryptedDatabase.saveDayData(dayData)
    }
}
