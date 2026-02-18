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
        val flowLevelValues = arrayOf (
            "Nothing" to R.drawable.nothing,
            R.string.spotting to R.drawable.spotting,
            R.string.light to R.drawable.drop1,
            R.string.moderate to R.drawable.drop2,
            R.string.heavy to R.drawable.drop3,
            R.string.omg to R.drawable.omg
        )

        val moodValues = arrayOf(
            R.string.happy to R.drawable.happy,
            "Energised" to R.drawable.energised,
            "Relaxed" to R.drawable.relaxed,
            R.string.sad to R.drawable.sad,
            "Unmotivated" to R.drawable.unmotivated,
            "Pessimistic" to R.drawable.pessimistic,
            "Irritable" to R.drawable.irritable,
            R.string.angry to R.drawable.angry
            "Anxious" to R.drawable.anxious
            "Stressed" to R.drawable.stressed
        )

        val painValues = arrayOf(
            "Abdominal pain" to R.drawable.abdominal_pain,
            "Backache" to R.drawable.backache,
            "Cramps" to R.drawable.cramps,
            "Headache" to R.drawable.headache,
            "Joint pain" to R.drawable.joint_pain,
            "Migraine" to R.drawable.migraine,
            "Muscle pain" to R.drawable.muscle_pain,
            "Ovulation Pain" to R.drawable.ovulation_pain,
            "Tender breasts" to R.drawable.tender_breasts
        )
        val symptomValues = arrayOf(
            "Acne" to R.drawable.acne,
            "Bloated", to R.drawable.bloated,
            "Burning Mouth" to R.drawable.burning_mouth,
            "Constipation" to R.drawable.constipation,
            "Cravings" to R.drawable.cravings,
            "Diarrhoea" to R.drawable.diarrhoea,
            "Discharge" to R.drawable.discharge,
            "Dizziness" to R.drawable.dizziness,
            "Fatigued" to R.drawable.fatigued,
            "Hot flashes" to R.drawable.hot_flashes,
            "Insomnia" to R.drawable.insomnia,
            "Itchiness" to R.drawable.itchiness,
            "Nausea" to R.drawable.nausea,
            "Stomach upset" to R.drawable.stomach_upset,
            "Sweating" to R.drawable.sweating,
            "UTI" to R.drawable.uti,
            "Vaginal dryness" to R.drawable.vaginal_dryness,
            "Vomiting" to R.drawable.vomiting
        )

        val activeValues = arrayOf(
            "Solo" to R.drawable.solo,
            "Partner" to R.drawable.partner,
            "Vaginal" to R.drawable.vaginal,
            "Oral" to R.drawable.oral,
            "Anal" to R.drawable.anal,
            "Toys" to R.drawable.toys
        )

        val protectionValues = arrayOf(
            "Condom" to R.drawable.condom,
            "Diaphragm" to R.drawable.diaphragm,
            "Sponge" to R.drawable.sponge,
            "Spermicide" to R.drawable.spermicide,
            "Emergency Contraception" to R.drawable.emergency_contraception
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
