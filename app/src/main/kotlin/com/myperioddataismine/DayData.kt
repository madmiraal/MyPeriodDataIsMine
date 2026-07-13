package com.myperioddataismine

import net.zetetic.database.sqlcipher.SQLiteDatabase
import java.util.Calendar
import kotlin.Int

class DayData {
    var date: Calendar = Calendar.getInstance()
    var bleedingFlowLevel: Int = 0
    var bleedingSmallClots: Int = 0
    var bleedingBigClots: Int = 0
    var bleedingExclude: Boolean = false
    var bleedingNotes: String = ""
    var moods: Int = 0
    var moodsNotes: String = ""
    var horny: Int = 0
    var pains: Int = 0
    var painsNotes: String = ""
    var symptoms: Int = 0
    var symptomsNotes: String = ""
    var sexActivities: Int = 0
    var sexProtections: Int = 0
    var sexNotes: String = ""
    var contraceptionPill: Boolean = false
    var contraceptionPatches: Int = 0
    var contraceptionIUD: Boolean = false
    var contraceptionRing: Boolean = false
    var contraceptionImplant: Boolean = false
    var contraceptionShot: Boolean = false
    var contraceptionNotes: String = ""
    var notes: String = ""

    fun isToday(): Boolean {
        val today = Calendar.getInstance()
        return today.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
            today.get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
            today.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
    }

    companion object {
        fun get(db: SQLiteDatabase, date: Calendar): DayData {
            val dayData = DayData()
            dayData.date = date.clone() as Calendar
            val cursor = db.query(
                "SELECT " +
                        "\"${PeriodDataTable.FLOW_LEVEL}\", " +
                        "\"${PeriodDataTable.SMALL_CLOTS}\", " +
                        "\"${PeriodDataTable.BIG_CLOTS}\", " +
                        "\"${PeriodDataTable.EXCLUDE}\", " +
                        "\"${PeriodDataTable.BLEEDING_NOTES}\", " +
                        "\"${PeriodDataTable.MOODS}\", " +
                        "\"${PeriodDataTable.MOODS_NOTES}\", " +
                        "\"${PeriodDataTable.HORNY}\", " +
                        "\"${PeriodDataTable.PAINS}\", " +
                        "\"${PeriodDataTable.PAINS_NOTES}\", " +
                        "\"${PeriodDataTable.SYMPTOMS}\", " +
                        "\"${PeriodDataTable.SYMPTOMS_NOTES}\", " +
                        "\"${PeriodDataTable.ACTIVITIES}\", " +
                        "\"${PeriodDataTable.PROTECTIONS}\", " +
                        "\"${PeriodDataTable.SEX_NOTES}\", " +
                        "\"${PeriodDataTable.PILL}\", " +
                        "\"${PeriodDataTable.PATCHES}\", " +
                        "\"${PeriodDataTable.IUD}\", " +
                        "\"${PeriodDataTable.RING}\", " +
                        "\"${PeriodDataTable.IMPLANT}\", " +
                        "\"${PeriodDataTable.SHOT}\", " +
                        "\"${PeriodDataTable.CONTRACEPTIVE_NOTES}\", " +
                        "\"${PeriodDataTable.NOTES}\"" +
                        "FROM \"${PeriodDataTable.TABLE_NAME}\" " +
                        "WHERE \"${PeriodDataTable.DATE}\" = ${PeriodDataTable.intFromCalendar(date)};"
            )
            cursor.moveToFirst()
            if (cursor.isAfterLast) return dayData
            dayData.bleedingFlowLevel = cursor.getInt(0)
            dayData.bleedingSmallClots = cursor.getInt(1)
            dayData.bleedingBigClots = cursor.getInt(2)
            dayData.bleedingExclude = cursor.getInt(3) != 0
            dayData.bleedingNotes = cursor.getString(4)
            dayData.moods = cursor.getInt(5)
            dayData.moodsNotes = cursor.getString(6)
            dayData.horny = cursor.getInt(7)
            dayData.pains = cursor.getInt(8)
            dayData.painsNotes = cursor.getString(9)
            dayData.symptoms = cursor.getInt(10)
            dayData.symptomsNotes = cursor.getString(11)
            dayData.sexActivities = cursor.getInt(12)
            dayData.sexProtections = cursor.getInt(13)
            dayData.sexNotes = cursor.getString(14)
            dayData.contraceptionPill = cursor.getInt(15) != 0
            dayData.contraceptionPatches = cursor.getInt(16)
            dayData.contraceptionIUD = cursor.getInt(17) != 0
            dayData.contraceptionRing = cursor.getInt(18) != 0
            dayData.contraceptionImplant = cursor.getInt(19) != 0
            dayData.contraceptionShot = cursor.getInt(20) != 0
            dayData.contraceptionNotes = cursor.getString(21)
            dayData.notes = cursor.getString(22)
            return dayData
        }

        fun save(dayData: DayData, db: SQLiteDatabase) {
            db.execSQL(
                "INSERT OR REPLACE INTO \"${PeriodDataTable.TABLE_NAME}\" (" +
                    "\"${PeriodDataTable.DATE}\", " +
                    "\"${PeriodDataTable.FLOW_LEVEL}\", " +
                    "\"${PeriodDataTable.SMALL_CLOTS}\", " +
                    "\"${PeriodDataTable.BIG_CLOTS}\", " +
                    "\"${PeriodDataTable.EXCLUDE}\", " +
                    "\"${PeriodDataTable.BLEEDING_NOTES}\", " +
                    "\"${PeriodDataTable.MOODS}\", " +
                    "\"${PeriodDataTable.MOODS_NOTES}\", " +
                    "\"${PeriodDataTable.HORNY}\", " +
                    "\"${PeriodDataTable.PAINS}\", " +
                    "\"${PeriodDataTable.PAINS_NOTES}\", " +
                    "\"${PeriodDataTable.SYMPTOMS}\", " +
                    "\"${PeriodDataTable.SYMPTOMS_NOTES}\", " +
                    "\"${PeriodDataTable.ACTIVITIES}\", " +
                    "\"${PeriodDataTable.PROTECTIONS}\", " +
                    "\"${PeriodDataTable.SEX_NOTES}\", " +
                    "\"${PeriodDataTable.PILL}\", " +
                    "\"${PeriodDataTable.PATCHES}\", " +
                    "\"${PeriodDataTable.IUD}\", " +
                    "\"${PeriodDataTable.RING}\", " +
                    "\"${PeriodDataTable.IMPLANT}\", " +
                    "\"${PeriodDataTable.SHOT}\", " +
                    "\"${PeriodDataTable.CONTRACEPTIVE_NOTES}\", " +
                    "\"${PeriodDataTable.NOTES}\"" +
                ") " +
                "VALUES (" +
                    "'${PeriodDataTable.intFromCalendar(dayData.date)}', " +
                    "${dayData.bleedingFlowLevel}, " +
                    "${dayData.bleedingSmallClots}, " +
                    "${dayData.bleedingBigClots}, " +
                    "${dayData.bleedingExclude}, " +
                    "'${dayData.bleedingNotes}', " +
                    "${dayData.moods}, " +
                    "'${dayData.moodsNotes}', " +
                    "${dayData.horny}, " +
                    "${dayData.pains}, " +
                    "'${dayData.painsNotes}', " +
                    "${dayData.symptoms}, " +
                    "'${dayData.symptomsNotes}', " +
                    "${dayData.sexActivities}, " +
                    "${dayData.sexProtections}, " +
                    "'${dayData.sexNotes}', " +
                    "${dayData.contraceptionPill}, " +
                    "${dayData.contraceptionPatches}, " +
                    "${dayData.contraceptionIUD}, " +
                    "${dayData.contraceptionRing}, " +
                    "${dayData.contraceptionImplant}, " +
                    "${dayData.contraceptionShot}, " +
                    "'${dayData.contraceptionNotes}', " +
                    "'${dayData.notes}'" +
                ");"
            )
        }
    }
}
