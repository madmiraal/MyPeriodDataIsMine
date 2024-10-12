package com.myperioddataismine

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.Calendar

class CalendarFragment(private val date: Calendar) : Fragment(R.layout.calendar_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val monthTextView = view.findViewById<TextView>(R.id.calendar_month)
        val yearTextView = view.findViewById<TextView>(R.id.calendar_year)
        val calendarGrid = view.findViewById<GridLayout>(R.id.calendar_grid)
        val month = date.get(Calendar.MONTH)
        val year = date.get(Calendar.YEAR)
        monthTextView.text = stringForMonth(month + 1)
        yearTextView.text = year.toString()
    }

    private fun stringForMonth(month: Int): String {
        return when (month) {
            1 -> getString(R.string.january)
            2 -> getString(R.string.february)
            3 -> getString(R.string.march)
            4 -> getString(R.string.april)
            5 -> getString(R.string.may)
            6 -> getString(R.string.june)
            7 -> getString(R.string.july)
            8 -> getString(R.string.august)
            9 -> getString(R.string.september)
            10 -> getString(R.string.october)
            11 -> getString(R.string.november)
            12 -> getString(R.string.december)
            else -> throw Exception("Invalid month: $month")
        }
    }
}
