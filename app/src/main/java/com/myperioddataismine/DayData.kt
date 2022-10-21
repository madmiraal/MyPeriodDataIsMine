package com.myperioddataismine

import net.zetetic.database.sqlcipher.SQLiteDatabase
import java.util.Calendar
import java.util.Date

class DayData {
    var date: Date = Calendar.getInstance().time
    var flowLevel: Int = 0
    var moods: Int = 0
    var symptoms: Int = 0

    enum class Field {
        FlowLevel,
        Moods,
        Symptoms
    }

    companion object: Table {
        val flowLevelValues = arrayOf (
            R.string.none to R.drawable.none,
            R.string.light to R.drawable.drop1,
            R.string.moderate to R.drawable.drop2,
            R.string.heavy to R.drawable.drop3,
            R.string.omg to R.drawable.drop4
        )

        val moodValues = arrayOf(
            R.string.happy to R.drawable.happy,
            R.string.sad to R.drawable.sad,
            R.string.tired to R.drawable.tired,
            R.string.sick to R.drawable.sick,
            R.string.angry to R.drawable.angry
        )

        val symptomValues = arrayOf(
            R.string.abdominal_pain to R.drawable.abdominal_pain,
            R.string.abdominal_swelling to R.drawable.abdominal_swelling,
            R.string.backache to R.drawable.backache,
            R.string.fatigue to R.drawable.fatigue,
            R.string.headache to R.drawable.headache,
            R.string.hip_pain to R.drawable.hip_pain,
            R.string.irritability to R.drawable.irritability,
            R.string.migraine_pain to R.drawable.migraine_pain,
            R.string.muscle_pain to R.drawable.muscle_pain,
            R.string.stomach_upset to R.drawable.stomach_upset,
            R.string.sweat to R.drawable.sweat,
            R.string.vomit to R.drawable.vomit
        )

        // Table Name
        private const val PERIOD_DATA: String = "Period Data"
        // Table columns
        private const val ID: String = "_id"
        private const val DATE: String = "Date"
        private const val FLOW_LEVEL: String = "Flow Level"
        private const val MOOD: String = "Mood"
        private const val SYMPTOMS: String = "Symptoms"

        override fun createTable(db: SQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE \"$PERIOD_DATA\" (" +
                    "\"$ID\" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "\"$DATE\" INTEGER NOT NULL UNIQUE, " +
                    "\"$FLOW_LEVEL\" INTEGER NOT NULL, " +
                    "\"$MOOD\" INTEGER NOT NULL, " +
                    "\"$SYMPTOMS\" INTEGER NOT NULL" +
                ");"
            )
        }

        override fun upgradeTable(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL(
                "DROP TABLE IF EXISTS \"$PERIOD_DATA\";"
            )
            createTable(db)
        }

        fun get(db:SQLiteDatabase, date: Date): DayData {
            val dayData = DayData()
            dayData.date = date
            val cursor = db.query(
                "SELECT " +
                "\"$FLOW_LEVEL\", " +
                "\"$MOOD\", " +
                "\"$SYMPTOMS\" " +
                "FROM \"$PERIOD_DATA\" " +
                "WHERE \"$DATE\" = ${intFromDate(date)};"
            )
            cursor.moveToFirst()
            if (cursor.isAfterLast) return dayData
            dayData.flowLevel = cursor.getInt(0)
            dayData.moods = cursor.getInt(1)
            dayData.symptoms = cursor.getInt(2)
            return dayData
        }

        fun save(dayData: DayData, db: SQLiteDatabase) {
            db.execSQL(
                "INSERT OR REPLACE INTO \"$PERIOD_DATA\" (" +
                    "\"$DATE\", " +
                    "\"$FLOW_LEVEL\", " +
                    "\"$MOOD\", " +
                    "\"$SYMPTOMS\"" +
                ") " +
                "VALUES(" +
                    "'${intFromDate(dayData.date)}', " +
                    "${dayData.flowLevel}, " +
                    "${dayData.moods}, " +
                    "${dayData.symptoms}" +
                ");"
            )
        }

        private fun intFromDate(date: Date): Int {
            val calendar = Calendar.getInstance()
            calendar.time = date
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val result = year * 10000 + month * 100 + day
            return result
        }
    }
}
