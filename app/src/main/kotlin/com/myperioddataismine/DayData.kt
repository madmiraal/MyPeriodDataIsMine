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

    enum class Field {
        FlowLevel,
        Moods,
        Symptoms
    }

    fun isToday(): Boolean {
        val today = Calendar.getInstance()
        return today.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
            today.get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
            today.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
    }

    companion object {
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
            R.string.energised to R.drawable.energised,
            R.string.relaxed to R.drawable.relaxed,
            R.string.sad to R.drawable.sad,
            R.string.unmotivated to R.drawable.unmotivated,
            R.string.pessimistic to R.drawable.pessimistic,
            R.string.irritable to R.drawable.irritable,
            R.string.angry to R.drawable.angry,
            R.string.anxious to R.drawable.anxious,
            R.string.stressed to R.drawable.stressed
        )

        val painValues = arrayOf(
            R.string.abdominal_pain to R.drawable.abdominal_pain,
            R.string.backache to R.drawable.backache,
            R.string.cramps to R.drawable.cramps_placeholder,
            R.string.headache to R.drawable.headache,
            R.string.joint_pain to R.drawable.joint_pain,
            R.string.migraine to R.drawable.migraine,
            R.string.muscle_pain to R.drawable.muscle_pain,
            R.string.ovulation_pain to R.drawable.ovulation_pain_placeholder,
            R.string.tender_breasts to R.drawable.tender_breasts_placeholder
        )
        val symptomValues = arrayOf(
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

        val activityValues = arrayOf(
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

        fun get(db:SQLiteDatabase, date: Calendar): DayData {
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
