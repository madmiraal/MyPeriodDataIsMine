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
            R.string.nothing to R.drawable.nothing,
            R.string.spotting to R.drawable.spotting,
            R.string.light to R.drawable.drop1,
            R.string.moderate to R.drawable.drop2,
            R.string.heavy to R.drawable.drop3,
            R.string.omg to R.drawable.omg
        )

        val moodValues = arrayOf(
            R.string.happy to R.drawable.happy,
            R.string.energised to R.drawable.energised_placeholder,
            R.string.relaxed to R.drawable.relaxed_placeholder,
            R.string.sad to R.drawable.sad,
            R.string.unmotivated to R.drawable.unmotivated_placeholder,
            R.string.pessimistic to R.drawable.pessimistic_placeholder,
            R.string.irritable to R.drawable.irritabie,
            R.string.angry to R.drawable.angry,
            R.string.anxious to R.drawable.anxious_placeholder,
            R.string.stressed to R.drawable.stressed_placeholder
        )

        val painValues = arrayOf(
            R.string.abdominal_pain to R.drawable.abdominal_pain,
            R.string.backache to R.drawable.backache,
            R.string.cramps to R.drawable.cramps_placeholder,
            R.string.headache to R.drawable.headache,
            R.string.joint_pain to R.drawable.joint_pain_placeholder,
            R.string.migraine to R.drawable.migraine_placeholder,
            R.string.muscle_pain to R.drawable.muscle_pain,
            R.string.ovulation_pain to R.drawable.ovulation_pain_placeholder,
            R.string.tender_breasts to R.drawable.tender_breasts_placeholder
        )
        val symptomsValues = arrayOf(
            R.string.acne to R.drawable.acne_placeholder,
            R.string.bloated to R.drawable.bloated,
            R.string.burning_mouth to R.drawable.burning_mouth_placeholder,
            R.string.constipation to R.drawable.constipation_placeholder,
            R.string.cravings to R.drawable.cravings_placeholder,
            R.string.diarrhoea to R.drawable.diarrhoea_placeholder,
            R.string.discharge to R.drawable.discharge_placeholder,
            R.string.dizziness to R.drawable.dizziness_placeholder,
            R.string.fatigued to R.drawable.fatigued,
            R.string.hot_flashes to R.drawable.hot_flashes_placeholder,
            R.string.insomnia to R.drawable.insomnia_placeholder,
            R.string.itchiness to R.drawable.itchiness_placeholder,
            R.string.nausea to R.drawable.nausea_placeholder,
            R.string.stomach_upset to R.drawable.stomach_upset,
            R.string.sweating to R.drawable.sweating,
            R.string.uti to R.drawable.uti_placeholder,
            R.string.vaginal_dryness to R.drawable.vaginal_dryness_placeholder,
            R.string.vomiting to R.drawable.vomiting
        )

        val activeValues = arrayOf(
            R.string.solo to R.drawable.solo_placeholder,
            R.string.partner to R.drawable.partner_placeholder,
            R.string.vaginal to R.drawable.vaginal_placeholder,
            R.string.oral to R.drawable.oral_placeholder,
            R.string.anal to R.drawable.anal_placeholder,
            R.string.toys to R.drawable.toys_placeholder
        )

        val protectionValues = arrayOf(
            R.string.condom to R.drawable.condom_placeholder,
            R.string.diaphragm to R.drawable.diaphragm_placeholder,
            R.string.sponge to R.drawable.sponge_placeholder,
            R.string.spermicide to R.drawable.spermicide_placeholder,
            R.string.emergency_contraception to R.drawable.emergency_contraception_placeholder
        )

        // Table names.
        private const val PERIOD_DATA: String = "Period Data"
        private const val CUSTOM_FIELDS: String = "Custom Fields"
        private const val CUSTOM_DATA: String = "Custom Data"

        // Primary key field.
        private const val ID: String = "_id"

        // Period Data table columns
        private const val DATE: String = "Date"
        // Bleeding fields
        private const val FLOW_LEVEL: String = "Flow Level"
        private const val SMALL_CLOTS: String = "Small Clots"
        private const val BIG_CLOTS: String = "Big Clots"
        private const val EXCLUDE: String = "Exclude"
        private const val BLEEDING_TEXT: String = "Bleeding Text"
        // Mood fields
        private const val MOOD: String = "Mood"
        private const val MOOD_TEXT: String = "Mood Text"
        // Horny field
        private const val HORNY: String = "Horny"
        // Pain fields
        private const val PAIN: String = "Pain"
        private const val PAIN_TEXT: String = "Pain Text"
        // Symptoms fields
        private const val SYMPTOMS: String = "Symptoms"
        private const val SYMPTOM_TEXT: String = "Symptoms Text"
        // Sex fields
        private const val ACTIVITY: String = "Activity"
        private const val PROTECTION: String = "Protection"
        private const val SEX_TEXT: String = "Sex Text"
        // Contraception fields
        private const val PILL: String = "Pill"
        private const val PATCH: String = "Patch"
        private const val IUD: String = "IUD"
        private const val RING: String = "Ring"
        private const val IMPLANT: String = "Implant"
        private const val SHOT: String = "Shot"
        private const val CONTRACEPTIVE_TEXT: String = "Contraceptive Text"
        private const val NOTES: String = "Notes"
        // V3 historical fields
        private const val BLEEDING: String = "Bleeding"

        // Custom Fields table columns
        private const val NAME: String = "Name"
        private const val ACTIVE: String = "Active"

        // Custom Data table columns
        private const val PERIOD_DATA_ID: String = "Period Data Id"
        private const val FIELD_NAME_ID: String = "Field Name Id"
        private const val CUSTOM_TEXT: String = "Custom Text"

        override fun createTable(db: SQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE \"$PERIOD_DATA\" (" +
                    "\"$ID\" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "\"$DATE\" INTEGER NOT NULL UNIQUE, " +
                    "\"$FLOW_LEVEL\" INTEGER NOT NULL, " +
                    "\"$SMALL_CLOTS\" INTEGER NOT NULL, " +
                    "\"$BIG_CLOTS\" INTEGER NOT NULL, " +
                    "\"$EXCLUDE\" BOOLEAN NOT NULL, " +
                    "\"$BLEEDING_TEXT\" TEXT NOT NULL, " +
                    "\"$MOOD\" INTEGER NOT NULL, " +
                    "\"$MOOD_TEXT\" TEXT NOT NULL, " +
                    "\"$HORNY\" INTEGER NOT NULL, " +
                    "\"$PAIN\" INTEGER NOT NULL, " +
                    "\"$PAIN_TEXT\" TEXT NOT NULL, " +
                    "\"$SYMPTOMS\" INTEGER NOT NULL, " +
                    "\"$SYMPTOM_TEXT\" TEXT NOT NULL, " +
                    "\"$ACTIVITY\" INTEGER NOT NULL, " +
                    "\"$PROTECTION\" INTEGER NOT NULL, " +
                    "\"$SEX_TEXT\" TEXT NOT NULL, " +
                    "\"$PILL\" BOOLEAN NOT NULL, " +
                    "\"$PATCH\" INTEGER NOT NULL, " +
                    "\"$IUD\" BOOLEAN NOT NULL, " +
                    "\"$RING\" BOOLEAN NOT NULL, " +
                    "\"$IMPLANT\" BOOLEAN NOT NULL, " +
                    "\"$SHOT\" BOOLEAN NOT NULL, " +
                    "\"$CONTRACEPTIVE_TEXT\" TEXT NOT NULL, " +
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
