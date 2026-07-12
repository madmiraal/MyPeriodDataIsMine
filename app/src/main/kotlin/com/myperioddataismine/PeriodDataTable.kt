package com.myperioddataismine

import net.zetetic.database.sqlcipher.SQLiteDatabase
import java.util.Calendar

class PeriodDataTable {
    companion object: Table {
        const val TABLE_NAME: String = "Period Data"

        // Table columns.
        // Primary key field.
        private const val ID: String = "_id"
        const val DATE: String = "Date"
        // Bleeding fields.
        const val FLOW_LEVEL: String = "Flow Level"
        const val SMALL_CLOTS: String = "Small Clots"
        const val BIG_CLOTS: String = "Big Clots"
        const val EXCLUDE: String = "Exclude"
        const val BLEEDING_NOTES: String = "Bleeding Notes"
        // Mood fields.
        const val MOODS: String = "Moods"
        const val MOODS_NOTES: String = "Moods Notes"
        // Horny field.
        const val HORNY: String = "Horny"
        // Pain fields.
        const val PAINS: String = "Pains"
        const val PAINS_NOTES: String = "Pains Notes"
        // Symptoms fields.
        const val SYMPTOMS: String = "Symptoms"
        const val SYMPTOMS_NOTES: String = "Symptoms Notes"
        // Sex fields.
        const val ACTIVITIES: String = "Activities"
        const val PROTECTIONS: String = "Protections"
        const val SEX_NOTES: String = "Sex Notes"
        // Contraception fields.
        const val PILL: String = "Pill"
        const val PATCHES: String = "Patches"
        const val IUD: String = "IUD"
        const val RING: String = "Ring"
        const val IMPLANT: String = "Implant"
        const val SHOT: String = "Shot"
        const val CONTRACEPTIVE_NOTES: String = "Contraceptive Notes"
        const val NOTES: String = "Notes"

        // Old column names.
        private const val MOOD: String = "Mood"

        override fun createTable(db: SQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE \"$TABLE_NAME\" (" +
                    "\"$ID\" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "\"$DATE\" INTEGER NOT NULL UNIQUE, " +
                    "\"$FLOW_LEVEL\" INTEGER NOT NULL, " +
                    "\"$SMALL_CLOTS\" INTEGER NOT NULL, " +
                    "\"$BIG_CLOTS\" INTEGER NOT NULL, " +
                    "\"$EXCLUDE\" BOOLEAN NOT NULL, " +
                    "\"$BLEEDING_NOTES\" TEXT NOT NULL, " +
                    "\"$MOODS\" INTEGER NOT NULL, " +
                    "\"$MOODS_NOTES\" TEXT NOT NULL, " +
                    "\"$HORNY\" INTEGER NOT NULL, " +
                    "\"$PAINS\" INTEGER NOT NULL, " +
                    "\"$PAINS_NOTES\" TEXT NOT NULL, " +
                    "\"$SYMPTOMS\" INTEGER NOT NULL, " +
                    "\"$SYMPTOMS_NOTES\" TEXT NOT NULL, " +
                    "\"$ACTIVITIES\" INTEGER NOT NULL, " +
                    "\"$PROTECTIONS\" INTEGER NOT NULL, " +
                    "\"$SEX_NOTES\" TEXT NOT NULL, " +
                    "\"$PILL\" BOOLEAN NOT NULL, " +
                    "\"$PATCHES\" INTEGER NOT NULL, " +
                    "\"$IUD\" BOOLEAN NOT NULL, " +
                    "\"$RING\" BOOLEAN NOT NULL, " +
                    "\"$IMPLANT\" BOOLEAN NOT NULL, " +
                    "\"$SHOT\" BOOLEAN NOT NULL, " +
                    "\"$CONTRACEPTIVE_NOTES\" TEXT NOT NULL, " +
                    "\"$NOTES\" TEXT NOT NULL" +
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
                            "DROP TABLE IF EXISTS \"$TABLE_NAME\";"
                        )
                        createTable(db)
                    }
                }
            }
        }

        private fun upgradeTableV2toV3(db: SQLiteDatabase) {
            db.execSQL("ALTER TABLE \"$TABLE_NAME\" RENAME TO \"Old $TABLE_NAME\"")
            createTable(db)
            val oldPeriodData = db.query(
                "SELECT " +
                    "\"$DATE\", " +
                    "\"$FLOW_LEVEL\", " +
                    "\"$MOOD\", " +
                    "\"$SYMPTOMS\" " +
                    "FROM \"Old $TABLE_NAME\";"
            )
            oldPeriodData.moveToFirst()
            while (!oldPeriodData.isAfterLast) {
                val oldDate = oldPeriodData.getInt(0)
                val oldFlowLevel = oldPeriodData.getInt(1)
                val oldMoods = oldPeriodData.getInt(2)
                val oldSymptoms = oldPeriodData.getInt(3)
                val dayData = DayData()
                dayData.date = calendarFromInt(oldDate)
                dayData.bleedingFlowLevel = oldFlowLevel
                dayData.moods = v3MoodsFromV2Values(oldMoods, oldSymptoms)
                dayData.pains = v3PainsFromV2Values(oldSymptoms)
                dayData.symptoms = v3SymptomsFromV2Values(oldMoods, oldSymptoms)
                DayData.save(dayData, db)
                oldPeriodData.moveToNext()
            }
            db.execSQL("DROP TABLE \"Old $TABLE_NAME\"")
        }
        private fun v3MoodsFromV2Values(oldMoods: Int, oldSymptoms: Int): Int {
            var moods = 0
            val moodMappings = arrayOf(
                0 to 0,   // Happy -> Happy
                1 to 3,   // Sad -> Sad
                2 to 4,   // Tired -> Unmotivated
                4 to 7    // Angry -> Angry
            )
            val symptomMappings = arrayOf(
                6 to 6,   // Irritability -> Irritable
            )
            for (moodMapping in moodMappings) {
                if ((oldMoods and (1 shl moodMapping.first)) > 0) {
                    moods = moods or (1 shl moodMapping.second)
                }
            }
            for (symptomMapping in symptomMappings) {
                if ((oldSymptoms and (1 shl symptomMapping.first)) > 0) {
                    moods = moods or (1 shl symptomMapping.second)
                }
            }
            return moods
        }

        private fun v3PainsFromV2Values(oldSymptoms: Int): Int {
            var pains = 0
            val symptomMappings = arrayOf(
                0 to 0, // Abdominal pain -> Abdominal pain
                2 to 1, // Backache -> Backache
                4 to 3, // Headache -> Headache
                5 to 4, // Hip pain -> Joint pain
                7 to 5, // Migraine pain -> Migraine
                8 to 6  // Muscle pain -> Muscle pain
            )
            for (symptomMapping in symptomMappings) {
                if ((oldSymptoms and (1 shl symptomMapping.first)) > 0) {
                    pains = pains or (1 shl symptomMapping.second)
                }
            }
            return pains
        }

        private fun v3SymptomsFromV2Values(oldMoods: Int, oldSymptoms: Int): Int {
            var symptoms = 0
            val moodMappings = arrayOf(
                3 to 12,   // Sick -> Nausea
            )
            val symptomMappings = arrayOf(
                1 to 1,   // Abdominal swelling -> Bloated
                3 to 8,   // Fatigue -> Fatigued
                9 to 13,  // Stomach upset -> Stomach upset
                10 to 14, // Sweat -> Sweating
                11 to 17  // Vomit -> Vomiting
            )
            for (moodMapping in moodMappings) {
                if ((oldMoods and (1 shl moodMapping.first)) > 0) {
                    symptoms = symptoms or (1 shl moodMapping.second)
                }
            }
            for (symptomMapping in symptomMappings) {
                if ((oldSymptoms and (1 shl symptomMapping.first)) > 0) {
                    symptoms = symptoms or (1 shl symptomMapping.second)
                }
            }
            return symptoms
        }

        fun intFromCalendar(date: Calendar): Int {
            val year = date.get(Calendar.YEAR)
            val month = date.get(Calendar.MONTH) + 1
            val day = date.get(Calendar.DAY_OF_MONTH)
            val result = year * 10000 + month * 100 + day
            return result
        }

        fun calendarFromInt(date: Int): Calendar {
            val result = Calendar.getInstance()
            val year = date / 10000
            val dateMinusYear = date - year * 10000
            val month = dateMinusYear / 100
            val day = dateMinusYear - month * 100
            result.set(year, month, day)
            return result
        }
    }
}
