package com.myperioddataismine

import net.zetetic.database.sqlcipher.SQLiteDatabase
import java.util.Calendar

class DayData {
    var date: Calendar = Calendar.getInstance()
    var bleeding: Int = 0
    var moods: Int = 0
    var symptoms: Int = 0

    enum class Field {
        Bleeding,
        Moods,
        Symptoms
    }

    fun isToday(): Boolean {
        val today = Calendar.getInstance()
        return intForDate(today) == intForDate(date)
    }

    companion object: Table {
        val bleedingValues = arrayOf (
            R.string.none to R.drawable.none,
            R.string.spotting to R.drawable.spotting,
            R.string.light to R.drawable.drop1,
            R.string.moderate to R.drawable.drop2,
            R.string.heavy to R.drawable.drop3,
            R.string.small_clots to R.drawable.small_clots,
            R.string.big_clots to R.drawable.big_clots,
            R.string.omg to R.drawable.omg
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
        private const val BLEEDING: String = "Bleeding"
        private const val MOOD: String = "Mood"
        private const val SYMPTOMS: String = "Symptoms"
        // V2 table columns
        private const val FLOW_LEVEL: String = "Flow Level"

        override fun createTable(db: SQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE \"$PERIOD_DATA\" (" +
                    "\"$ID\" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "\"$DATE\" INTEGER NOT NULL UNIQUE, " +
                    "\"$BLEEDING\" INTEGER NOT NULL, " +
                    "\"$MOOD\" INTEGER NOT NULL, " +
                    "\"$SYMPTOMS\" INTEGER NOT NULL" +
                ");"
            )
        }

        override fun upgradeTable(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            var currentVersion = oldVersion
            while (currentVersion < newVersion) {
                when (currentVersion) {
                    2 -> {
                        upgradeTableV2toV3(db)
                        currentVersion++
                    }
                    else -> {
                        println("Failed to upgrade database from $oldVersion to $newVersion")
                        println("Creating a new database")
                        db.execSQL(
                            "DROP TABLE IF EXISTS \"$PERIOD_DATA\";"
                        )
                        createTable(db)
                    }
                }
            }
        }

        fun get(db:SQLiteDatabase, date: Calendar): DayData {
            val dayData = DayData()
            dayData.date = date.clone() as Calendar
            val cursor = db.query(
                "SELECT " +
                "\"$BLEEDING\", " +
                "\"$MOOD\", " +
                "\"$SYMPTOMS\" " +
                "FROM \"$PERIOD_DATA\" " +
                "WHERE \"$DATE\" = ${intForDate(date)};"
            )
            cursor.moveToFirst()
            if (cursor.isAfterLast) return dayData
            dayData.bleeding = cursor.getInt(0)
            dayData.moods = cursor.getInt(1)
            dayData.symptoms = cursor.getInt(2)
            return dayData
        }

        fun save(dayData: DayData, db: SQLiteDatabase) {
            db.execSQL(
                "INSERT OR REPLACE INTO \"$PERIOD_DATA\" (" +
                    "\"$DATE\", " +
                    "\"$BLEEDING\", " +
                    "\"$MOOD\", " +
                    "\"$SYMPTOMS\"" +
                ") " +
                "VALUES (" +
                    "'${intForDate(dayData.date)}', " +
                    "${dayData.bleeding}, " +
                    "${dayData.moods}, " +
                    "${dayData.symptoms}" +
                ");"
            )
        }

        private fun intForDate(date: Calendar): Int {
            val year = date.get(Calendar.YEAR)
            val month = date.get(Calendar.MONTH) + 1
            val day = date.get(Calendar.DAY_OF_MONTH)
            val result = year * 10000 + month * 100 + day
            return result
        }

        private fun upgradeTableV2toV3(db: SQLiteDatabase) {
            db.execSQL("ALTER TABLE \"$PERIOD_DATA\" RENAME TO \"Old $PERIOD_DATA\"")
            createTable(db)
            db.execSQL(
                "INSERT INTO \"$PERIOD_DATA\" (" +
                    "\"$DATE\", " +
                    "\"$BLEEDING\", " +
                    "\"$MOOD\", " +
                    "\"$SYMPTOMS\"" +
                ") SELECT " +
                "\"$DATE\", " +
                "1 << \"$FLOW_LEVEL\", " +
                "\"$MOOD\", " +
                "\"$SYMPTOMS\" " +
                "FROM \"Old $PERIOD_DATA\";"
            )
            db.execSQL("DROP TABLE \"Old $PERIOD_DATA\"")
        }
    }
}
